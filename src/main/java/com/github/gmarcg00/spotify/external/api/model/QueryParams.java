package com.github.gmarcg00.spotify.external.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Guillermo Marcos García
 *
 * <p> Represents the query parameters for retrieving entities information. </p>
 *
 * <p> This class encapsulates the parameters used to query entities,
 * including the specific track identifiers. </p>
 */
@NoArgsConstructor
@Builder
@Getter
@Setter
public class QueryParams {
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
     * @param ids an array of track identifiers, stored in {@code this.ids}.
     */
    public QueryParams(String[] ids){
        this.ids = ids;
    }
}
