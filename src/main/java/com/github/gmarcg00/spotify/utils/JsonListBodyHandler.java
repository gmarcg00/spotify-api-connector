package com.github.gmarcg00.spotify.utils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class JsonListBodyHandler<T> extends AbstractJsonBodyHandler<List<T>> {

    private final TypeReference<List<T>> typeReference;

    public JsonListBodyHandler(TypeReference<List<T>> typeReference){
        this.typeReference = typeReference;
    }

    @Override
    protected List<T> convert(String body) {
        return this.mapper.map(body,typeReference);
    }
}
