package com.payment.digestor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigInteger;

@Value
public class Transaction {
    @JsonProperty("tx_id")
    String id;
    @JsonProperty("tx_currency")
    String currency;
    @JsonProperty("tx_amount_cents")
    BigInteger amountInCents;
    @JsonProperty("credit_card")
    CreditCard creditCard;
}
