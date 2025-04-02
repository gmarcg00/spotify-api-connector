package com.github.gmarcg00.spotify.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GlobalMapper {

    private static GlobalMapper instance;
    private final ObjectMapper mapper;

    private GlobalMapper(){
        this.mapper = new ObjectMapper();
        configure();
    }

    public static GlobalMapper getInstance(){
        if(instance == null){
            instance = new GlobalMapper();
        }
        return instance;
    }

    private void configure(){
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public <T> T map(String json, Class<T> valueType) {
        try {
            return this.mapper.readValue(json,valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T map(String json, TypeReference<T> valueTypeRef) {
        try {
            return this.mapper.readValue(json,valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
