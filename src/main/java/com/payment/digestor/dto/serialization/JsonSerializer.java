package com.payment.digestor.dto.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class JsonSerializer<T> extends StdSerializer<T> {

    private ObjectMapper mapper = new ObjectMapper();

    public JsonSerializer() {
        this(null);
    }

    protected JsonSerializer(Class<T> t) {
        super(t);
    }

    @Override
    public void serialize(T type,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(mapper.writeValueAsString(type));
    }
}
