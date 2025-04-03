package com.github.gmarcg00.spotify.external.api.model.response.episode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import com.github.gmarcg00.spotify.data.other.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class EpisodeResponse {
    @JsonProperty(value = "audio_preview_url")
    private String audioPreviewUrl;
    private String description;
    @JsonProperty(value = "duration_ms")
    private long duration;
    private boolean explicit;
    @JsonProperty(value = "external_urls")
    private ExternalUrl externalUrl;
    private String href;
    @JsonProperty(value = "html_description")
    private String htmlDescription;
    private String id;
    private List<Image> images;
    @JsonProperty(value = "is_externally_hosted")
    private boolean isExternallyHosted;
    @JsonProperty(value = "is_playable")
    private boolean isPlayable;
    private String language;
    private List<String> languages;
    private String name;
    @JsonProperty(value = "release_date")
    private Date releaseDate;
    private PodcastInfo show;
    private String type;
    private String uri;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class PodcastInfo {
        @JsonProperty(value = "available_markets")
        private List<String> availableMarkets;
        private String description;
        private boolean explicit;
        @JsonProperty(value = "external_urls")
        private ExternalUrl externalUrl;
        private String href;
        @JsonProperty(value = "html_description")
        private String htmlDescription;
        private String id;
        private List<Image> images;
        @JsonProperty(value = "is_externally_hosted")
        private boolean isExternallyHosted;
        private List<String> languages;
        @JsonProperty(value = "media_type")
        private String mediaType;
        private String name;
        private String publisher;
        @JsonProperty(value = "total_episodes")
        private String totalEpisodes;
        private String type;
        private String uri;
    }

}
