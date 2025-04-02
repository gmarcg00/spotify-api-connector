package com.github.gmarcg00.spotify.external.api.mapper;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumResponse;


public class AlbumMapper {

    private AlbumMapper(){}

    public static Album toEntity(AlbumResponse response){
        return Album.builder()
                .albumType(response.getAlbumType())
                .totalTracks(response.getTotalTracks())
                .availableMarkets(response.getAvailableMarkets())
                .externalUrl(response.getExternalUrl())
                .href(response.getHref())
                .id(response.getId())
                .images(response.getImages())
                .name(response.getName())
                .releaseDate(response.getReleaseDate().toInstant())
                .type(response.getType())
                .uri(response.getUri())
                .artists(response.getArtists().stream().map(AlbumMapper::getArtist).toList())
                .tracks(response.getTracks().getItems().stream().map(AlbumMapper::getTrack).toList())
                .copyrights(response.getCopyrights())
                .popularity(response.getPopularity())
                .build();

    }

    private static Artist getArtist(AlbumResponse.Artist artist){
        return Artist.builder()
                .externalUrl(artist.getExternalUrl())
                .href(artist.getHref())
                .id(artist.getId())
                .name(artist.getName())
                .type(artist.getType())
                .uri(artist.getUri())
                .build();
    }

    private static Track getTrack(AlbumResponse.Tracks.TrackItem trackItem){
        return null;
    }
}
