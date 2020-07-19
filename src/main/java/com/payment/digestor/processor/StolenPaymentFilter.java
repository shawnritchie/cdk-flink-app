package com.payment.digestor.processor;

import com.payment.digestor.dto.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FilterFunction;

@Slf4j
public class StolenPaymentFilter implements FilterFunction<PaymentEvent> {
    @Override
    public boolean filter(PaymentEvent paymentEvent) {
        log.error("PROCESSING STOLEN CARD");
        return paymentEvent.getPaymentMethod().getCardState().equals("STOLEN");
    }
}
