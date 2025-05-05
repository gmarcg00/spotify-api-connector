package com.github.gmarcg00.spotify.config;

import com.github.gmarcg00.spotify.exception.InternalServerException;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class Config {

    private Config(){}

    public static final String ARTISTS_PATH;
    public static final String ALBUMS_PATH;
    public static final String ALBUMS_NEW_RELEASES_PATH;
    public static final String EPISODES_PATH;
    public static final String TRACKS_PATH;
    public static final String PLAYLISTS_PATH;
    public static final String MARKETS_PATH;
    public static final String ACCESS_TOKEN_PATH;

    static {
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("application.yml")){
            if (inputStream == null) {
                throw new FileNotFoundException("application.yml file not found.");
            }

            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);

            ARTISTS_PATH = (String) config.get("artists");
            ALBUMS_PATH = (String) config.get("albums");
            ALBUMS_NEW_RELEASES_PATH = (String) config.get("albumsNewReleases");
            EPISODES_PATH = (String) config.get("episodes");
            TRACKS_PATH = (String) config.get("tracks");
            PLAYLISTS_PATH = (String) config.get("playlists");
            MARKETS_PATH = (String) config.get("markets");
            ACCESS_TOKEN_PATH = (String) config.get("accessToken");

        }catch (Exception e){
            throw new InternalServerException("Error loading config.yml: " + e.getMessage());
        }
    }

}
