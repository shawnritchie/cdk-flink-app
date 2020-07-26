package com.payment.digestor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class CreditCard {
    @JsonProperty("card_type")
    String cardType;
    @JsonProperty("card_owner")
    String cardOwner;
    @JsonProperty("card_number")
    String cardNumber;
    @JsonProperty("card_cvc")
    String cardCVC;
    @JsonProperty("card_state")
    String cardState;
}
