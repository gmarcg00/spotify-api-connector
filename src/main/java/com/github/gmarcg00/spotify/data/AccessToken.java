package com.github.gmarcg00.spotify.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AccessToken {
    private String accessToken;
    private String tokenType;
    private int expiresIn;
}
