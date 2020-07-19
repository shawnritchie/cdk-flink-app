package com.payment.digestor.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.digestor.dto.PaymentEvent;
import com.payment.digestor.dto.PaymentWrapper;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichMapFunction;

@Slf4j
@Value
@EqualsAndHashCode(callSuper=false)
public class PaymentEventDeserializationMap extends RichMapFunction<String, PaymentWrapper> {

    private final ObjectMapper mapper;

    public PaymentEventDeserializationMap(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PaymentWrapper map(String input) throws Exception {
        return mapper.readValue(input, PaymentWrapper.class);
    }
}