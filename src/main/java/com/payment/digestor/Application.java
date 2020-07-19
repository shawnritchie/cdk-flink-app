package com.payment.digestor;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.payment.digestor.dto.PaymentAlert;
import com.payment.digestor.dto.PaymentEvent;
import com.payment.digestor.dto.PaymentWrapper;
import com.payment.digestor.processor.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kinesis.FlinkKinesisConsumer;
import org.apache.flink.streaming.connectors.kinesis.FlinkKinesisProducer;
import org.apache.flink.streaming.connectors.kinesis.config.ConsumerConfigConstants;

import java.util.Properties;

@Slf4j
public class Application {
    private static final String region = "us-east-1";
    private static final String inputStreamName = "PaymentStream";
    private static final String outputStreamName = "PaymentStream";

    private static final ObjectMapper objectMapper = createObjectMapper();

    private static DataStream<PaymentWrapper> createSource(StreamExecutionEnvironment env) {
        Properties inputProperties = new Properties();
        inputProperties.setProperty(ConsumerConfigConstants.AWS_REGION, region);
        inputProperties.setProperty(ConsumerConfigConstants.STREAM_INITIAL_POSITION, "LATEST");

        FlinkKinesisConsumer kinesisSrouce = new FlinkKinesisConsumer<>(
                inputStreamName,
                new SimpleStringSchema(),
                inputProperties);

        return env
                .addSource(kinesisSrouce)
                .map(new PaymentEventDeserializationMap(objectMapper))
                .assignTimestampsAndWatermarks(new EventTimeExtractor())
                .name("Payment Source")
                .uid("payment_source");
    }

    private static FlinkKinesisProducer<String> createSink() {
        Properties outputProperties = new Properties();
        outputProperties.setProperty(ConsumerConfigConstants.AWS_REGION, region);
        outputProperties.setProperty("AggregationEnabled", "false");

        FlinkKinesisProducer<String> sink = new FlinkKinesisProducer<>(new SimpleStringSchema(), outputProperties);
        sink.setDefaultStream(outputStreamName);
        sink.setDefaultPartition("0");
        return sink;
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setInjectableValues(new InjectableValues.Std().addValue("objectMapper", mapper));
        return mapper;
    }

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<PaymentWrapper> inputStream = createSource(env);

        DataStream<String> paymentAlertStream =
                inputStream
                        .filter(input -> input instanceof PaymentEvent)
                        .map(paymentWrapper -> (PaymentEvent) paymentWrapper)
                        .filter(new StolenPaymentFilter())
                        .windowAll(TumblingEventTimeWindows.of(Time.seconds(5)))
                        .process(new PaymentAlertProcessWindow())
                        .map(paymentAlert -> objectMapper.writeValueAsString(paymentAlert))
                        .name("payment_alerts")
                        .uid("payment_alerts")
                        .startNewChain();

        paymentAlertStream
                .addSink(createSink())
                .name("Payment Alert Sink")
                .uid("payment_alert_sink");

        DataStream<String> paymentAlertSummaryStream =
                inputStream
                        .filter(input -> input instanceof PaymentAlert)
                        .map(paymentWrapper -> (PaymentAlert) paymentWrapper)
                        .keyBy(new StolenCardSelector())
                        .window(SlidingEventTimeWindows.of(Time.minutes(1), Time.seconds(10)))
                        .aggregate(new PaymentAlertAggregator())
                        .map(paymentAlert -> objectMapper.writeValueAsString(paymentAlert))
                        .name("payment_alerts_summary")
                        .uid("payment_alerts_summary")
                        .startNewChain();

        paymentAlertSummaryStream
                .addSink(createSink())
                .name("Payment Alert Summary Sink")
                .uid("payment_alert_summary_sink");

        env.execute("Flink Streaming Java API Skeleton");
    }
}