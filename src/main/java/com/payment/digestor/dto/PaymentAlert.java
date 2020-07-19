package com.payment.digestor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.payment.digestor.dto.serialization.JsonSerializer;
import com.payment.digestor.dto.serialization.PaymentMethodDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentAlert extends PaymentWrapper {
    @JsonProperty("event_json_data")
    @JsonSerialize(using = JsonSerializer.class)
    @JsonDeserialize(using = PaymentMethodDeserializer.class)
    PaymentMethod paymentMethod;
}
