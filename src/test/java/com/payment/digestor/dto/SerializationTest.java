package com.payment.digestor.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SerializationTest {

    private static final String PAYMENT_EVENT_STRING = "{\"event_created\": \"2020-07-18 09:02:10\", \"event_type\": \"PAYMENT_EVENT\", \"event_json_data\": \"{\\\"card_type\\\": \\\"Mastercard\\\", \\\"card_owner\\\": \\\"Renee Garner\\\", \\\"card_number\\\": \\\"2502895861855452\\\", \\\"card_cvc\\\": \\\"01/28\\\", \\\"card_state\\\": \\\"LEGIT\\\"}\"}";
    private static final String PAYMENT_ALERT_STRING = "{\"event_created\": \"2020-07-18 09:02:10\", \"event_type\": \"PAYMENT_ALERT\", \"event_json_data\": \"{\\\"card_type\\\": \\\"Mastercard\\\", \\\"card_owner\\\": \\\"Renee Garner\\\", \\\"card_number\\\": \\\"2502895861855452\\\", \\\"card_cvc\\\": \\\"01/28\\\", \\\"card_state\\\": \\\"STOLEN\\\"}\"}";
    private static final String PAYMENT_ALERT_SUMMARY_STRING = "{\"event_created\": \"2020-07-18 09:02:10\", \"event_type\": \"PAYMENT_ALERT_SUMMARY\", \"event_json_data\": \"{\\\"number_of_deposits\\\": 10, \\\"total_deposits_in_cents\\\": 10000, \\\"payment_method\\\": {\\\"card_type\\\": \\\"Mastercard\\\", \\\"card_owner\\\": \\\"Renee Garner\\\", \\\"card_number\\\": \\\"2502895861855452\\\", \\\"card_cvc\\\": \\\"01/28\\\", \\\"card_state\\\": \\\"STOLEN\\\"}}\"}";
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void paymentEventSerializationTest() throws IOException {
        PaymentWrapper paymentWrapper = mapper.readValue(PAYMENT_EVENT_STRING, PaymentWrapper.class);
        String jsonPaymentEvent = mapper.writeValueAsString(paymentWrapper);
        paymentWrapper = mapper.readValue(jsonPaymentEvent, PaymentWrapper.class);

        Assert.assertTrue(paymentWrapper instanceof PaymentEvent);
        PaymentEvent paymentEvent = (PaymentEvent)paymentWrapper;
        Assert.assertNotNull(paymentEvent);
        Assert.assertEquals(
                paymentEvent.getEventCreated(),
                LocalDateTime.of(
                        LocalDate.of(2020, 07, 18),
                        LocalTime.of(9, 2, 10)));

        Assert.assertNotNull(paymentEvent.getPaymentMethod());
        Assert.assertEquals("Mastercard", paymentEvent.getPaymentMethod().getCardType());
        Assert.assertEquals("Renee Garner", paymentEvent.getPaymentMethod().getCardOwner());
        Assert.assertEquals("2502895861855452", paymentEvent.getPaymentMethod().getCardNumber());
        Assert.assertEquals("01/28", paymentEvent.getPaymentMethod().getCardCVC());
        Assert.assertEquals("LEGIT", paymentEvent.getPaymentMethod().getCardState());
    }

    @Test
    public void paymentAlertSerializationTest() throws IOException {
        PaymentWrapper paymentWrapper = mapper.readValue(PAYMENT_ALERT_STRING, PaymentWrapper.class);
        String jsonPaymentEvent = mapper.writeValueAsString(paymentWrapper);
        paymentWrapper = mapper.readValue(jsonPaymentEvent, PaymentWrapper.class);

        Assert.assertTrue(paymentWrapper instanceof PaymentAlert);
        PaymentAlert paymentAlert = (PaymentAlert)paymentWrapper;
        Assert.assertNotNull(paymentAlert);
        Assert.assertEquals(
                paymentAlert.getEventCreated(),
                LocalDateTime.of(
                        LocalDate.of(2020, 07, 18),
                        LocalTime.of(9, 2, 10)));

        Assert.assertNotNull(paymentAlert.getPaymentMethod());
        Assert.assertEquals("Mastercard", paymentAlert.getPaymentMethod().getCardType());
        Assert.assertEquals("Renee Garner", paymentAlert.getPaymentMethod().getCardOwner());
        Assert.assertEquals("2502895861855452", paymentAlert.getPaymentMethod().getCardNumber());
        Assert.assertEquals("01/28", paymentAlert.getPaymentMethod().getCardCVC());
        Assert.assertEquals("STOLEN", paymentAlert.getPaymentMethod().getCardState());
    }

    @Test
    public void paymentAlertSummarySerializationTest() throws IOException {
        PaymentWrapper paymentWrapper = mapper.readValue(PAYMENT_ALERT_SUMMARY_STRING, PaymentWrapper.class);
        String jsonPaymentEvent = mapper.writeValueAsString(paymentWrapper);
        paymentWrapper = mapper.readValue(jsonPaymentEvent, PaymentWrapper.class);

        Assert.assertTrue(paymentWrapper instanceof PaymentAlertSummary);
        PaymentAlertSummary PaymentAlertSummary = (PaymentAlertSummary)paymentWrapper;
        Assert.assertNotNull(PaymentAlertSummary);
        Assert.assertEquals(
                PaymentAlertSummary.getEventCreated(),
                LocalDateTime.of(
                        LocalDate.of(2020, 07, 18),
                        LocalTime.of(9, 2, 10)));

        Assert.assertNotNull(PaymentAlertSummary.getPaymentSummary());
        Assert.assertEquals(BigInteger.TEN, PaymentAlertSummary.getPaymentSummary().getNumberOfDeposits());
        Assert.assertEquals(BigInteger.valueOf(10000), PaymentAlertSummary.getPaymentSummary().getTotalDeposits());

        Assert.assertNotNull(PaymentAlertSummary.getPaymentSummary().getPaymentMethod());
        PaymentMethod paymentMethod = PaymentAlertSummary.getPaymentSummary().getPaymentMethod();
        Assert.assertEquals("Mastercard", paymentMethod.getCardType());
        Assert.assertEquals("Renee Garner", paymentMethod.getCardOwner());
        Assert.assertEquals("2502895861855452", paymentMethod.getCardNumber());
        Assert.assertEquals("01/28", paymentMethod.getCardCVC());
        Assert.assertEquals("STOLEN", paymentMethod.getCardState());

    }
}
