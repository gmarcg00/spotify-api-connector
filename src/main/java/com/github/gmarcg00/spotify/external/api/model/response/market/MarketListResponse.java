package com.github.gmarcg00.spotify.external.api.model.response.market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MarketListResponse {
    private List<String> markets;
}
