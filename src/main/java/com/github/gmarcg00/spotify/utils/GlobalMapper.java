package com.github.gmarcg00.spotify.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gmarcg00.spotify.exception.JsonMappingException;

public class GlobalMapper {

    private final ObjectMapper mapper;

    public GlobalMapper(){
        this.mapper = new ObjectMapper();
        configure();
    }

    private void configure(){
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public <T> T map(String json, Class<T> valueType) {
        try {
            return this.mapper.readValue(json,valueType);
        } catch (JsonProcessingException e) {
            throw new JsonMappingException(e.getMessage());
        }
    }
}
