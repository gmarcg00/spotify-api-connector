package com.github.gmarcg00.spotify.service.impl;

import com.github.gmarcg00.spotify.exception.SpotifyApiException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.market.MarketListResponse;
import com.github.gmarcg00.spotify.service.MarketService;
import com.github.gmarcg00.spotify.service.utils.ServiceUtils;

import java.util.List;

import static com.github.gmarcg00.spotify.config.Config.MARKETS_PATH;

/**
 * @author Guillermo Marcos García
 *
 * <p> Implementation of MarketService interface </p>
 */
public class MarketServiceImpl implements MarketService {

    private final Executor executor;

    public MarketServiceImpl(Executor executor){
        this.executor = executor;
    }

    @Override
    public List<String> getMarkets(String token) throws SpotifyApiException {
        ServiceUtils.checkNullValues(token);
        MarketListResponse response = executor.get(MARKETS_PATH,token, MarketListResponse.class);
        return response.getMarkets();
    }
}
