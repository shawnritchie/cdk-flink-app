package com.payment.digestor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.payment.digestor.dto.serialization.CustomDateDeserializer;
import com.payment.digestor.dto.serialization.CustomLocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "event_type", include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = PaymentEvent.class, name = "PAYMENT_EVENT"),
        @JsonSubTypes.Type(value = PaymentAlert.class, name = "PAYMENT_ALERT"),
        @JsonSubTypes.Type(value = PaymentAlertSummary.class, name = "PAYMENT_ALERT_SUMMARY")
})
@Data
public abstract class PaymentWrapper {
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("event_created")
    LocalDateTime eventCreated;
}
