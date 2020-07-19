package com.payment.digestor.dto.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.payment.digestor.dto.PaymentMethod;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class PaymentMethodDeserializer extends StdDeserializer<PaymentMethod> {

    ObjectMapper mapper = new ObjectMapper();

    public PaymentMethodDeserializer() {
        this(null);
    }

    protected PaymentMethodDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PaymentMethod deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        return mapper.readValue(jsonParser.getText(), PaymentMethod.class);
    }

}
