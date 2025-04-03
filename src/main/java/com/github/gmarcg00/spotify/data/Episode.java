package com.github.gmarcg00.spotify.data;

import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import com.github.gmarcg00.spotify.data.other.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Episode {
    private String audioPreviewUrl;
    private String description;
    private long duration;
    private boolean explicit;
    private ExternalUrl externalUrl;
    private String href;
    private String htmlDescription;
    private String id;
    private List<Image> images;
    private boolean isExternallyHosted;
    private boolean isPlayable;
    private String language;
    private List<String> languages;
    private String name;
    private Instant releaseDate;
    private Podcast podcast;
    private String type;
    private String uri;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Podcast {
        private List<String> availableMarkets;
        private String description;
        private boolean explicit;
        private ExternalUrl externalUrl;
        private String href;
        private String htmlDescription;
        private String id;
        private List<Image> images;
        private boolean isExternallyHosted;
        private List<String> languages;
        private String mediaType;
        private String name;
        private String publisher;
        private String totalEpisodes;
        private String type;
        private String uri;
    }
}
