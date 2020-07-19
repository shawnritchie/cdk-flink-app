package com.payment.digestor.dto.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.payment.digestor.dto.PaymentMethod;
import com.payment.digestor.dto.PaymentSummary;

import java.io.IOException;

public class PaymentSummaryDeserializer extends StdDeserializer<PaymentSummary> {

    ObjectMapper mapper = new ObjectMapper();

    public PaymentSummaryDeserializer() {
        this(null);
    }

    protected PaymentSummaryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PaymentSummary deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        return mapper.readValue(jsonParser.getText(), PaymentSummary.class);
    }

}
