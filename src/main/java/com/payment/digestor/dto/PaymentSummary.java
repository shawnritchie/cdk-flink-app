package com.payment.digestor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class PaymentSummary {
    @JsonProperty("credit_card")
    CreditCard creditCard;
    @JsonProperty("number_of_tx")
    BigInteger numberOfDeposits;
    @JsonProperty("total_tx_in_cents")
    BigInteger totalDeposits;

    public void aggregateDeposit(BigInteger depositAmount) {
        this.numberOfDeposits = this.numberOfDeposits.add(BigInteger.ONE);
        this.totalDeposits = this.totalDeposits.add(depositAmount);
    }
}
