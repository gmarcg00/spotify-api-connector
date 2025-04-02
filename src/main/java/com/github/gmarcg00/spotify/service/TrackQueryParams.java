package com.github.gmarcg00.spotify.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Guillermo Marcos García
 *
 * <p> Represents the query parameters for retrieving track information. </p>
 *
 * <p> This class encapsulates the parameters used to query tracks,
 * including the market in which the information is to be retrieved
 * and the specific track identifiers. </p>
 */
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TrackQueryParams {

    /**
     * ISO 3166-1 alpha-2 country code representing the market.
     *
     * <p> This parameter is optional and is used to specify the market
     * in which track information is to be retrieved. </p>
     */
    private String market;

    /**
     * List of track identifiers.
     *
     * <p> These identifiers are expected to be unique strings corresponding
     * to specific tracks in the queried database. </p>
     */
    private String [] ids;

    /**
     * Constructs a new instance of {@code TrackQueryParams}.
     *
     * @param market the market code in ISO 3166-1 alpha-2 format, stored in {@code this.market}.
     * @param ids an array of track identifiers, stored in {@code this.ids}.
     */
    public TrackQueryParams(String market, String[] ids){
        this.market = market;
        this.ids = ids;
    }

}
