package com.github.gmarcg00.spotify;

import com.github.gmarcg00.spotify.data.*;
import com.github.gmarcg00.spotify.data.other.Copyright;
import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import com.github.gmarcg00.spotify.data.other.Follower;
import com.github.gmarcg00.spotify.data.other.Image;
import com.github.gmarcg00.spotify.external.api.model.response.album.AlbumResponse;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class TestCommonData {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private TestCommonData(){}

    public static Artist getArtist(){
        return Artist.builder()
                .externalUrl(new ExternalUrl("spotify_url"))
                .followers(new Follower())
                .href("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy")
                .id("4aawyAB9vmqN3uQ7FjRGTy")
                .images(List.of())
                .name("name")
                .popularity(60)
                .type("artist")
                .uri("spotify:album:4aawyAB9vmqN3uQ7FjRGTy")
                .build();
    }

    public static ArtistResponse getArtistResponse(){
        Image image1 = new Image("https://i.scdn.co/image/ab6761610000e5ebf70cb5cef7d3848570900de5",640,640);
        Image image2 = new Image("https://i.scdn.co/image/ab67616100005174f70cb5cef7d3848570900de5",320,320);
        Image image3 = new Image("https://i.scdn.co/image/ab6761610000f178f70cb5cef7d3848570900de5",160,160);
        return ArtistResponse.builder()
                .externalUrl(new ExternalUrl("https://open.spotify.com/artist/7eLcDZDYHXZCebtQmVFL25"))
                .followers(new Follower(null,3242935))
                .href("https://api.spotify.com/v1/artists/7eLcDZDYHXZCebtQmVFL25")
                .id("7eLcDZDYHXZCebtQmVFL25")
                .images(List.of(image1,image2,image3))
                .name("Aitana")
                .popularity(76)
                .type("artist")
                .uri("spotify:artist:7eLcDZDYHXZCebtQmVFL25")
                .build();
    }

    public static AlbumResponse getAlbumResponse() throws ParseException {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Image image1 = new Image("https://i.scdn.co/image/ab67616d0000b273e1b22ace42c98117458010b4",640,640);
        return AlbumResponse.builder()
                .albumType("single")
                .totalTracks(1)
                .isPlayable(true)
                .externalUrl(new ExternalUrl("https://open.spotify.com/album/64vx3cUb97lQGlgt8zozWL"))
                .href("https://api.spotify.com/v1/albums/64vx3cUb97lQGlgt8zozWL?market=ES")
                .id("64vx3cUb97lQGlgt8zozWL")
                .images(List.of(image1))
                .name("Paris")
                .releaseDate(dateFormat.parse("2017-01-13"))
                .type("album")
                .uri("spotify:album:64vx3cUb97lQGlgt8zozWL")
                .artists(List.of(getAlbumResponseArtist()))
                .tracks(getAlbumResponseTracks())
                .copyrights(List.of(new Copyright("(P) 2017 Disruptor Records/Columbia Records","P")))
                .label("Disruptor Records/Columbia")
                .popularity(54)
                .build();
    }

    public static AlbumResponse.Artist getAlbumResponseArtist(){
        return AlbumResponse.Artist.builder()
                .externalUrl(new ExternalUrl("https://open.spotify.com/artist/69GGBxA162lTqCwzJG5jLp"))
                .href("https://api.spotify.com/v1/artists/69GGBxA162lTqCwzJG5jLp")
                .id("69GGBxA162lTqCwzJG5jLp")
                .name("The Chainsmokers")
                .type("artist")
                .uri("spotify:artist:69GGBxA162lTqCwzJG5jLp")
                .build();
    }

    public static AlbumResponse.Tracks getAlbumResponseTracks(){
        return AlbumResponse.Tracks.builder()
                .href("https://api.spotify.com/v1/albums/64vx3cUb97lQGlgt8zozWL/tracks?offset=0&limit=50&market=ES")
                .total(1)
                .previous(null)
                .next(null)
                .items(List.of(getAlbumTrackItem()))
                .build();
    }

    public static AlbumResponse.Tracks.TrackItem getAlbumTrackItem(){
        return AlbumResponse.Tracks.TrackItem.builder()
                .artists(List.of(getAlbumResponseArtist()))
                .discNumber(1)
                .duration(221520)
                .explicit(false)
                .externalUrl(new ExternalUrl("https://open.spotify.com/track/15vzANxN8G9wWfwAJLLMCg"))
                .href("https://api.spotify.com/v1/tracks/15vzANxN8G9wWfwAJLLMCg")
                .id("15vzANxN8G9wWfwAJLLMCg")
                .isPlayable(true)
                .name("Paris")
                .trackNumber(1)
                .type("track")
                .uri("spotify:track:15vzANxN8G9wWfwAJLLMCg")
                .isLocal(true)
                .build();
    }
}
