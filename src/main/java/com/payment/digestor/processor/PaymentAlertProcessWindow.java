package com.payment.digestor.processor;

import com.payment.digestor.dto.PaymentAlert;
import com.payment.digestor.dto.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

@Slf4j
public class PaymentAlertProcessWindow extends ProcessAllWindowFunction<PaymentEvent, PaymentAlert, TimeWindow> {

    @Override
    public void process(Context context, Iterable<PaymentEvent> iterable, Collector<PaymentAlert> collector)
            throws Exception {
        iterable.forEach(paymentEvent -> {
            log.error("CREATING PAYMENT ALERT");
            PaymentAlert paymentAlert = new PaymentAlert(paymentEvent.getTransaction());
            paymentAlert.setEventCreated(paymentEvent.getEventCreated());
            collector.collect(paymentAlert);
        });
    }
}
