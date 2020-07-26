package com.payment.digestor.dto.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.payment.digestor.dto.CreditCard;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CreditCardDeserializer extends StdDeserializer<CreditCard> {

    ObjectMapper mapper = new ObjectMapper();

    public CreditCardDeserializer() {
        this(null);
    }

    protected CreditCardDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CreditCard deserialize(JsonParser jsonParser,
                                  DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        return mapper.readValue(jsonParser.getText(), CreditCard.class);
    }

}
