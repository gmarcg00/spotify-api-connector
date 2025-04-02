package com.github.gmarcg00.spotify.utils;

import com.github.tomakehurst.wiremock.WireMockServer;

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
}
