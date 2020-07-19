package com.payment.digestor.processor;

import com.payment.digestor.dto.PaymentAlert;
import org.apache.flink.api.java.functions.KeySelector;

public class StolenCardSelector implements KeySelector<PaymentAlert, String> {
    @Override
    public String getKey(PaymentAlert paymentAlert) {
        return paymentAlert.getPaymentMethod().getCardNumber();
    }
}
