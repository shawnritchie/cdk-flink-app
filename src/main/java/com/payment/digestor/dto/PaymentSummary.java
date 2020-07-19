package com.payment.digestor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class PaymentSummary {
    @JsonProperty("payment_method")
    PaymentMethod paymentMethod;
    @JsonProperty("number_of_deposits")
    BigInteger numberOfDeposits;
    @JsonProperty("total_deposits_in_cents")
    BigInteger totalDeposits;

    public void aggregateDeposit(BigInteger depositAmount) {
        this.numberOfDeposits = this.numberOfDeposits.add(BigInteger.ONE);
        this.totalDeposits = this.totalDeposits.add(depositAmount);
    }
}
