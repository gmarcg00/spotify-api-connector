package com.github.gmarcg00.spotify.external.api.mapper;

import com.github.gmarcg00.spotify.data.*;
import com.github.gmarcg00.spotify.external.api.model.response.playlist.PlaylistResponse;

import java.util.Objects;

public class PlaylistMapper {

    private static final String TRACK_TYPE = "track";
    private static final String EPISODE_TYPE = "episode";

    private PlaylistMapper(){}

    public static Playlist toEntity(PlaylistResponse response){
        return Playlist.builder()
                .collaborative(response.isCollaborative())
                .description(response.getDescription())
                .externalUrl(response.getExternalUrl())
                .followers(response.getFollowers())
                .href(response.getHref())
                .id(response.getId())
                .images(response.getImages())
                .name(response.getName())
                .owner(toEntity(response.getOwner()))
                .primaryColor(response.getPrimaryColor())
                .isPublic(response.isPublic())
                .snapshotId(response.getSnapshotId())
                .tracks(response.getTracks().getItems().stream()
                        .filter(t -> Objects.equals(t.getTrack().getType(), TRACK_TYPE))
                        .map(t -> toTrackEntity(t.getTrack()))
                        .toList())
                .episodes(response.getTracks().getItems().stream()
                        .filter(t -> Objects.equals(t.getTrack().getType(), EPISODE_TYPE))
                        .map(t -> toEpisodeEntity(t.getTrack()))
                        .toList())
                .type(response.getType())
                .uri(response.getUri())
                .build();
    }

    private static User toEntity(PlaylistResponse.Owner owner){
        return User.builder()
                .displayName(owner.getDisplayName())
                .externalUrl(owner.getExternalUrl())
                .href(owner.getHref())
                .id(owner.getId())
                .type(owner.getType())
                .uri(owner.getUri())
                .build();
    }

    private static Track toTrackEntity(PlaylistResponse.Item.PlaylistElement playlistElement){
        return Track.builder()
                .availableMarkets(playlistElement.getAvailableMarkets())
                .explicit(playlistElement.isExplicit())
                .type(playlistElement.getType())
                .album(toEntity(playlistElement.getAlbum()))
                .artists(playlistElement.getArtists().stream().map(PlaylistMapper::toEntity).toList())
                .discNumber(playlistElement.getDiscNumber())
                .duration(playlistElement.getDuration())
                .externalUrl(playlistElement.getExternalUrl())
                .href(playlistElement.getHref())
                .id(playlistElement.getId())
                .name(playlistElement.getName())
                .popularity(playlistElement.getPopularity())
                .trackNumber(playlistElement.getTrackNumber())
                .uri(playlistElement.getUri())
                .build();
    }

    private static Episode toEpisodeEntity(PlaylistResponse.Item.PlaylistElement playlistElement){
        var album = playlistElement.getAlbum();
        Episode.Podcast podcast = Episode.Podcast.builder()
                .availableMarkets(album.getAvailableMarkets())
                .type(album.getType())
                .href(album.getHref())
                .id(album.getId())
                .images(album.getImages())
                .name(album.getName())
                .uri(album.getUri())
                .build();
        return Episode.builder()
                .type(playlistElement.getType())
                .href(playlistElement.getHref())
                .externalUrl(playlistElement.getExternalUrl())
                .id(playlistElement.getId())
                .name(playlistElement.getName())
                .uri(podcast.getUri())
                .podcast(podcast)
                .build();
    }

    private static Album toEntity(PlaylistResponse.Item.PlaylistElement.Album album){
        return Album.builder()
                .availableMarkets(album.getAvailableMarkets())
                .type(album.getType())
                .albumType(album.getAlbumType())
                .href(album.getHref())
                .id(album.getId())
                .images(album.getImages())
                .name(album.getName())
                .releaseDate(album.getReleaseDate().toInstant())
                .uri(album.getUri())
                .externalUrl(album.getExternalUrl())
                .artists(album.getArtists().stream().map(PlaylistMapper::toEntity).toList())
                .totalTracks(album.getTotalTracks())
                .build();
    }

    private static Artist toEntity(PlaylistResponse.Item.Artist artist){
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
