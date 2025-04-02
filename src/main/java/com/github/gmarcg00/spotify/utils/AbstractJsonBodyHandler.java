package com.github.gmarcg00.spotify.utils;

import java.net.http.HttpResponse;
import java.nio.charset.Charset;

public abstract class AbstractJsonBodyHandler<T> implements HttpResponse.BodyHandler<T> {

    protected final GlobalMapper mapper = GlobalMapper.getInstance();

    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(
                HttpResponse.BodySubscribers.ofString(Charset.defaultCharset()),
                this::convert
        );
    }

    protected abstract T convert(String body);
}
