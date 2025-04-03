package com.github.gmarcg00.spotify.external.api.mapper;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.external.api.model.response.track.TrackResponse;

import java.util.List;

public class TrackMapper {

    private TrackMapper(){}

    public static Track toEntity(TrackResponse response){
        List<Artist> artists = response.getArtists().stream()
                .map(TrackMapper::toEntity)
                .toList();
        return Track.builder()
                .album(toEntity(response.getAlbum()))
                .artists(artists)
                .availableMarkets(response.getAvailableMarkets())
                .discNumber(response.getDiscNumber())
                .duration(response.getDuration())
                .explicit(response.isExplicit())
                .href(response.getHref())
                .id(response.getId())
                .isLocal(response.isLocal())
                .name(response.getName())
                .popularity(response.getPopularity())
                .trackNumber(response.getTrackNumber())
                .type(response.getType())
                .uri(response.getUri())
                .build();
    }

    private static Album toEntity(TrackResponse.Album album){
        return Album.builder()
                .albumType(album.getAlbumType())
                .totalTracks(album.getTotalTracks())
                .availableMarkets(album.getAvailableMarkets())
                .externalUrl(album.getExternalUrl())
                .href(album.getHref())
                .id(album.getId())
                .images(album.getImages())
                .name(album.getName())
                .releaseDate(album.getReleaseDate().toInstant())
                .type(album.getType())
                .uri(album.getUri())
                .artists(album.getArtists().stream().map(TrackMapper::toEntity).toList())
                .build();
    }

    private static Artist toEntity(TrackResponse.Artist artist){
        return Artist.builder()
                .externalUrl(artist.getExternalUrl())
                .href(artist.getHref())
                .id(artist.getId())
                .name(artist.getName())
                .type(artist.getType())
                .uri(artist.getUri())
                .build();
    }
}
