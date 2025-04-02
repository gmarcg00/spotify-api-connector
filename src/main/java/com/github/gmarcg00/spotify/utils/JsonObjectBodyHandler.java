package com.github.gmarcg00.spotify.utils;

public class JsonObjectBodyHandler<T> extends AbstractJsonBodyHandler<T> {

    private final Class<T> type;

    public JsonObjectBodyHandler(Class<T> type){
        this.type = type;
    }

    @Override
    protected T convert(String body) {
        return this.mapper.map(body,type);
    }
}
