package com.github.gmarcg00.spotify.external.api.mapper;

import com.github.gmarcg00.spotify.data.Album;
import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistAlbumsResponse;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistResponse;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistTopTracksResponse;


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

    public static Album toEntity(ArtistAlbumsResponse.AlbumItem item){
        return Album.builder()
                .albumType(item.getAlbumType())
                .totalTracks(item.getTotalTracks())
                .availableMarkets(item.getAvailableMarkets())
                .externalUrl(item.getExternalUrl())
                .href(item.getHref())
                .id(item.getId())
                .images(item.getImages())
                .name(item.getName())
                .releaseDate(item.getReleaseDate().toInstant())
                .type(item.getType())
                .uri(item.getUri())
                .artists(item.getArtists().stream().map(ArtistMapper::toEntity).toList())
                .build();
    }

    public static Artist toEntity(ArtistAlbumsResponse.AlbumItem.Artist item){
        return Artist.builder()
                .externalUrl(item.getExternalUrl())
                .href(item.getHref())
                .id(item.getId())
                .name(item.getName())
                .type(item.getType())
                .uri(item.getUri())
                .build();
    }

    public static Track toEntity(ArtistTopTracksResponse.TrackItem item){
        return Track.builder()
                .album(toEntity(item.getAlbum()))
                .artists(item.getArtists().stream().map(ArtistMapper::toEntity).toList())
                .availableMarkets(item.getAvailableMarkets())
                .discNumber(item.getDiscNumber())
                .duration(item.getDuration())
                .explicit(item.isExplicit())
                .externalUrl(item.getExternalUrl())
                .href(item.getHref())
                .id(item.getId())
                .isLocal(item.isLocal())
                .name(item.getName())
                .popularity(item.getPopularity())
                .trackNumber(item.getTrackNumber())
                .type(item.getType())
                .uri(item.getUri())
                .build();
    }

    public static Album toEntity(ArtistTopTracksResponse.TrackItem.Album item){
        return Album.builder()
                .albumType(item.getAlbumType())
                .totalTracks(item.getTotalTracks())
                .availableMarkets(item.getAvailableMarkets())
                .externalUrl(item.getExternalUrl())
                .href(item.getHref())
                .id(item.getId())
                .images(item.getImages())
                .name(item.getName())
                .releaseDate(item.getReleaseDate().toInstant())
                .type(item.getType())
                .uri(item.getUri())
                .artists(item.getArtists().stream().map(ArtistMapper::toEntity).toList())
                .build();
    }

    public static Artist toEntity(ArtistTopTracksResponse.TrackItem.Artist item){
        return Artist.builder()
                .externalUrl(item.getExternalUrl())
                .href(item.getHref())
                .id(item.getId())
                .name(item.getName())
                .type(item.getType())
                .uri(item.getUri())
                .build();
    }
}
