package com.payment.digestor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.payment.digestor.dto.serialization.JsonSerializer;
import com.payment.digestor.dto.serialization.TransactionDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentEvent extends PaymentWrapper {
    @JsonProperty("event_json_data")
    @JsonSerialize(using = JsonSerializer.class)
    @JsonDeserialize(using = TransactionDeserializer.class)
    Transaction transaction;
}
