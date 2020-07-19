package com.payment.digestor;

import com.payment.digestor.dto.PaymentAlert;
import com.payment.digestor.dto.PaymentEvent;
import com.payment.digestor.dto.PaymentMethod;
import com.payment.digestor.processor.PaymentAlertProcessWindow;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.util.Collector;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PaymentAlertTest {

    @Test
    public void PaymentAlertProcessWindowTest() throws Exception {
        //instantiate user-defined function
        PaymentAlertProcessWindow paymentAlertProcessWindow = new PaymentAlertProcessWindow();

        PaymentMethod paymentMethod = new PaymentMethod("cardType", "cardOwner",
                "cardNumber", "cardCVC", "cardState", BigInteger.TEN);
        Iterable<PaymentEvent> paymentEventIterator = Collections.singletonList(new PaymentEvent(paymentMethod));
        Collector<PaymentAlert> paymentAlertCollector = mock(Collector.class);
        paymentAlertProcessWindow.process(mock(ProcessAllWindowFunction.Context.class),
                paymentEventIterator, paymentAlertCollector);

        verify(paymentAlertCollector).collect(any(PaymentAlert.class));
    }
}
