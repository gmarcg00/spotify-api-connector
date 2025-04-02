package com.github.gmarcg00.spotify.external.api.model.response.artist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import com.github.gmarcg00.spotify.data.other.Image;
import com.github.gmarcg00.spotify.data.other.Follower;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ArtistResponse {
    @JsonProperty(value = "external_urls")
    private ExternalUrl externalUrl;
    private Follower followers;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private int popularity;
    private String type;
    private String uri;
}
