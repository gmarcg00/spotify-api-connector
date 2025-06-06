package com.github.gmarcg00.spotify.external.api.mapper;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumResponse;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumTracksResponse;

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
                .tracks(response.getTracks() != null ? response.getTracks().getItems().stream().map(AlbumMapper::getTrack).toList() : null)
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
        return Track.builder()
                .artists(trackItem.getArtists().stream().map(AlbumMapper::getArtist).toList())
                .availableMarkets(trackItem.getAvailableMarkets())
                .discNumber(trackItem.getDiscNumber())
                .duration(trackItem.getDuration())
                .explicit(trackItem.isExplicit())
                .externalUrl(trackItem.getExternalUrl())
                .href(trackItem.getHref())
                .id(trackItem.getId())
                .name(trackItem.getName())
                .trackNumber(trackItem.getTrackNumber())
                .type(trackItem.getType())
                .uri(trackItem.getUri())
                .build();
    }

    public static Track toEntity(AlbumTracksResponse.TrackItem item){
        return Track.builder()
                .artists(item.getArtists().stream().map(AlbumMapper::toEntity).toList())
                .availableMarkets(item.getAvailableMarkets())
                .discNumber(item.getDiscNumber())
                .duration(item.getDuration())
                .explicit(item.isExplicit())
                .externalUrl(item.getExternalUrl())
                .href(item.getHref())
                .id(item.getId())
                .name(item.getName())
                .trackNumber(item.getTrackNumber())
                .type(item.getType())
                .uri(item.getUri())
                .isLocal(item.isLocal())
                .build();
    }

    private static Artist toEntity(AlbumTracksResponse.Artist artist){
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
