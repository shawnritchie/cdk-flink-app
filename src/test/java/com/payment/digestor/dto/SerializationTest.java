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

    private static final String PAYMENT_EVENT_STRING = "{\"event_created\": \"2020-07-18 09:02:10\", \"event_type\": \"PAYMENT_EVENT\", \"event_json_data\": \"{\\\"tx_id\\\": \\\"eb77162ef3644cf1bacee1dbad66c5fd\\\", \\\"tx_amount_cents\\\": 5000, \\\"tx_currency\\\": \\\"EUR\\\", \\\"credit_card\\\": {\\\"card_type\\\": \\\"Mastercard\\\", \\\"card_owner\\\": \\\"Renee Garner\\\", \\\"card_number\\\": \\\"2502895861855452\\\", \\\"card_cvc\\\": \\\"01/28\\\", \\\"card_state\\\": \\\"LEGIT\\\"}}\"}";
    private static final String PAYMENT_ALERT_STRING = "{\"event_created\": \"2020-07-18 09:02:10\", \"event_type\": \"PAYMENT_ALERT\", \"event_json_data\": \"{\\\"tx_id\\\": \\\"eb77162ef3644cf1bacee1dbad66c5fd\\\", \\\"tx_amount_cents\\\": 5000, \\\"tx_currency\\\": \\\"EUR\\\", \\\"credit_card\\\": {\\\"card_type\\\": \\\"Mastercard\\\", \\\"card_owner\\\": \\\"Renee Garner\\\", \\\"card_number\\\": \\\"2502895861855452\\\", \\\"card_cvc\\\": \\\"01/28\\\", \\\"card_state\\\": \\\"STOLEN\\\"}}\"}";
    private static final String PAYMENT_ALERT_SUMMARY_STRING = "{\"event_created\": \"2020-07-18 09:02:10\", \"event_type\": \"PAYMENT_ALERT_SUMMARY\", \"event_json_data\": \"{\\\"number_of_tx\\\": 10, \\\"total_tx_in_cents\\\": 10000, \\\"credit_card\\\": {\\\"card_type\\\": \\\"Mastercard\\\", \\\"card_owner\\\": \\\"Renee Garner\\\", \\\"card_number\\\": \\\"2502895861855452\\\", \\\"card_cvc\\\": \\\"01/28\\\", \\\"card_state\\\": \\\"STOLEN\\\"}}\"}";
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

        Transaction tx = paymentEvent.getTransaction();
        Assert.assertNotNull(tx);
        Assert.assertEquals("eb77162ef3644cf1bacee1dbad66c5fd", tx.getId());
        Assert.assertEquals(BigInteger.valueOf(5000l), tx.getAmountInCents());
        Assert.assertEquals("EUR", tx.getCurrency());

        CreditCard cc = paymentEvent.getTransaction().getCreditCard();
        Assert.assertNotNull(cc);
        Assert.assertEquals("Mastercard", cc.getCardType());
        Assert.assertEquals("Renee Garner", cc.getCardOwner());
        Assert.assertEquals("2502895861855452", cc.getCardNumber());
        Assert.assertEquals("01/28", cc.getCardCVC());
        Assert.assertEquals("LEGIT", cc.getCardState());
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

        Transaction tx = paymentAlert.getTransaction();
        Assert.assertNotNull(tx);
        Assert.assertEquals("eb77162ef3644cf1bacee1dbad66c5fd", tx.getId());
        Assert.assertEquals(BigInteger.valueOf(5000l), tx.getAmountInCents());
        Assert.assertEquals("EUR", tx.getCurrency());

        CreditCard cc = paymentAlert.getTransaction().getCreditCard();
        Assert.assertNotNull(cc);
        Assert.assertEquals("Mastercard", cc.getCardType());
        Assert.assertEquals("Renee Garner", cc.getCardOwner());
        Assert.assertEquals("2502895861855452", cc.getCardNumber());
        Assert.assertEquals("01/28", cc.getCardCVC());
        Assert.assertEquals("STOLEN", cc.getCardState());
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

        Assert.assertNotNull(PaymentAlertSummary.getPaymentSummary().getCreditCard());
        CreditCard creditCard = PaymentAlertSummary.getPaymentSummary().getCreditCard();
        Assert.assertEquals("Mastercard", creditCard.getCardType());
        Assert.assertEquals("Renee Garner", creditCard.getCardOwner());
        Assert.assertEquals("2502895861855452", creditCard.getCardNumber());
        Assert.assertEquals("01/28", creditCard.getCardCVC());
        Assert.assertEquals("STOLEN", creditCard.getCardState());

    }
}
