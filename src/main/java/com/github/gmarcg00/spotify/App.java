package com.github.gmarcg00.spotify;


import com.github.gmarcg00.spotify.data.Artist;
import com.github.gmarcg00.spotify.data.Track;
import com.github.gmarcg00.spotify.exception.EntityNotFoundException;
import com.github.gmarcg00.spotify.exception.UnauthorizedException;
import com.github.gmarcg00.spotify.external.api.Executor;
import com.github.gmarcg00.spotify.external.api.model.response.artist.ArtistResponse;
import com.github.gmarcg00.spotify.service.ArtistService;
import com.github.gmarcg00.spotify.service.TrackQueryParams;
import com.github.gmarcg00.spotify.service.TrackService;
import com.github.gmarcg00.spotify.service.impl.ArtistServiceImpl;
import com.github.gmarcg00.spotify.service.impl.TrackServiceImpl;

import java.util.List;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String API_HOST = "https://api.spotify.com/v1";
        String ARTISTS_ENDPOINT = "/artists";
        Executor<ArtistResponse> executor = new Executor<>(API_HOST + ARTISTS_ENDPOINT);
        TrackService trackService = new TrackServiceImpl();
        ArtistService artistService = new ArtistServiceImpl(executor);
        Track miAmor = null;
        List<Track> tracks = null;
        String[] ids = new String[2];
        ids[0] = "7eLcDZDYHXZCebtQmVFL25";
        ids[1] = "2CIMQHirSU0MQqyYHq0eOx";
        TrackQueryParams params = new TrackQueryParams("ES",ids);
        try {
            //Artist artist = artistService.getArtist("7eLcDZDYHXZCebtQmVFL25","BQAYb4tPWEzOymtEXiKZHwQCm4DlNvFqxDwaqPpl_HQVwgz-A5IIClSTTj_-GkVwXFsbDDGC5uZI_ooLbW96VKSkRgjYJtIwT-ys4wkkJW3ennlCZAIuBTUNKO4G10ucn94V5PZCs10");
            //System.out.println(artist);
            List<Artist> artists = artistService.getArtists(ids,"BQAYb4tPWEzOymtEXiKZHwQCm4DlNvFqxDwaqPpl_HQVwgz-A5IIClSTTj_-GkVwXFsbDDGC5uZI_ooLbW96VKSkRgjYJtIwT-ys4wkkJW3ennlCZAIuBTUNKO4G10ucn94V5PZCs10");
            System.out.println(artists);
            miAmor = trackService.getTrack("1zTzz7nUxA2UxE6NhNTWSF","ES","BQAFlTIEXxfIlQLVEljHS1mErMUXTP_yYQw0yqiXFsS1aOznEF-mrpYPJ7wL5woj5ISto6SIhDjEDUV6J-lGs_pl8Sje2V1J-PyF1mUQR8rULbMypk0LrWNcWWgLw583Y67Qo-u-SfM");
            tracks = trackService.getTracks(params,"BQCc3u8yPM8_wlnhY7vuKjiwqnrG4J_DIaZN9_p8TyFwjCgzov0Gv45X3_tTP-3PbVkmwhDGqKYYw89JhGCb3qE55AM-O2c-Eim74_d-MmYzrOriR6Xc_PQPJ6r8eL-zagUEJhdX1t8");
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnauthorizedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(miAmor);
        System.out.println(tracks);
    }
}
