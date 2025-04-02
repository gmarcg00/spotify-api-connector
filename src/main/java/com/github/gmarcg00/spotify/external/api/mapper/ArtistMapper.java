package com.github.gmarcg00.spotify.external.api.mapper;

import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistResponse;

import java.util.List;

public class ArtistMapper {

    private ArtistMapper(){}

    public static Artist toEntity(ArtistResponse response){
        return Artist.builder()
                .externalUrl(response.getExternalUrl())
                .followers(response.getFollowers())
                .href(response.getHref())
                .id(response.getId())
                .images(response.getImages())
                .name(response.getName())
                .popularity(response.getPopularity())
                .type(response.getType())
                .uri(response.getUri())
                .build();
    }

    public static List<Artist> toEntityList(List<ArtistResponse> response){
        return response.stream()
                .map(ArtistMapper::toEntity)
                .toList();
    }
}
