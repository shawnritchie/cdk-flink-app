package com.payment.digestor.processor;

import com.payment.digestor.dto.PaymentAlert;
import com.payment.digestor.dto.PaymentAlertSummary;
import com.payment.digestor.dto.PaymentSummary;
import org.apache.flink.api.common.functions.AggregateFunction;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class PaymentAlertAggregator implements AggregateFunction<PaymentAlert, PaymentSummary, PaymentAlertSummary> {

    @Override
    public PaymentSummary createAccumulator() {
        return new PaymentSummary(null, BigInteger.ZERO, BigInteger.ZERO);
    }

    @Override
    public PaymentSummary add(PaymentAlert paymentAlert, PaymentSummary paymentSummary) {
        if (paymentSummary.getCreditCard() == null) {
            paymentSummary.setCreditCard(paymentAlert.getTransaction().getCreditCard());
        }

        paymentSummary.aggregateDeposit(paymentAlert.getTransaction().getAmountInCents());
        return paymentSummary;
    }

    @Override
    public PaymentAlertSummary getResult(PaymentSummary paymentSummary) {
        PaymentAlertSummary paymentAlertSummary = new PaymentAlertSummary(paymentSummary);
        paymentAlertSummary.setEventCreated(LocalDateTime.now());
        return paymentAlertSummary;
    }

    @Override
    public PaymentSummary merge(PaymentSummary paymentSummary, PaymentSummary acc1) {
        return new PaymentSummary(
                paymentSummary.getCreditCard() != null ? paymentSummary.getCreditCard() : acc1.getCreditCard(),
                paymentSummary.getNumberOfDeposits().add(acc1.getNumberOfDeposits()),
                paymentSummary.getTotalDeposits().add(acc1.getTotalDeposits()));
    }
}
