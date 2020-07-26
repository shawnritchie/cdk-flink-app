package com.payment.digestor.dto.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.payment.digestor.dto.Transaction;

import java.io.IOException;

public class TransactionDeserializer extends StdDeserializer<Transaction> {

    ObjectMapper mapper = new ObjectMapper();

    public TransactionDeserializer() {
        this(null);
    }

    protected TransactionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Transaction deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        return mapper.readValue(jsonParser.getText(), Transaction.class);
    }
}
