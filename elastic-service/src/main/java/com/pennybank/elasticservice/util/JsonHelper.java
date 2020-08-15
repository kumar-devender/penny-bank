package com.pennybank.elasticservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pennybank.elasticservice.errors.MalFormedJsonException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonHelper {

    @NonNull
    private final ObjectMapper mapper;

    public String toJson(Object object) {
        return toJson(object, false);
    }

    public String toJson(Object object, boolean pretty) {
        try {
            ObjectWriter writer = pretty ? mapper.writer() : mapper.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error marshalling object [" + object + "] to json", e);
        }
    }

    public String prettify(String json) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readValue(json, Map.class));
        } catch (IOException e) {
            throw new RuntimeException("Malformed json string:\n" + json, e);
        }
    }

    public <K, V> Map<K, V> toMap(final String json) {
        try {
            //noinspection unchecked
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            throw new MalFormedJsonException("Unable to transform meta to map: \n" + json, e);
        }
    }

    public <T> T fromJson(String str, Class<T> clazz) {
        try {
            return mapper.readValue(str, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Not a valid json string for unmarshalling", e);
        }
    }

    public <T> T fromJson(String str, TypeReference<T> typeRef) {
        try {
            return mapper.readValue(str, typeRef);
        } catch (IOException e) {
            throw new IllegalArgumentException("Not a valid json string for unmarshalling", e);
        }
    }

}
