package com.github.gmarcg00.spotify.utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockHelper {

    private static final WireMockServer mockServer = new WireMockServer();

    public static WireMockServer getServer(){
        return mockServer;
    }

    public static void mockGetRequest(String path,int status,String bodyFile){
        mockServer.stubFor(get(urlEqualTo(path))
                .willReturn(aResponse()
                        .withStatus(status)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile(bodyFile)
                )
        );
    }

    public static void mockPostRequest(String path, int status, Map<String, String> headers, String requestBody, String bodyFile){
        MappingBuilder builder = post(urlEqualTo(path))
                .withRequestBody(equalTo(requestBody));
        if (headers != null) {
            headers.forEach((key, value) -> builder.withHeader(key, equalTo(value)));
        }
        builder.willReturn(aResponse()
                .withStatus(status)
                .withBodyFile(bodyFile)
        );
        mockServer.stubFor(builder);
    }
}
