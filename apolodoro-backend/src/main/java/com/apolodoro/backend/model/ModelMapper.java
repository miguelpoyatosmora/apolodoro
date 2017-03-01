package com.apolodoro.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;

public enum ModelMapper {

    INSTANCE;

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
            .registerModule(new GuavaModule());

    public <T> T map(String json, TypeReference<T> typeRef){

        try {
            return mapper.readValue(json, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T map(byte[] jsonAsBytes, TypeReference<T> typeRef) {
        try {

            T value = mapper.readValue(jsonAsBytes, typeRef);

            return value;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
