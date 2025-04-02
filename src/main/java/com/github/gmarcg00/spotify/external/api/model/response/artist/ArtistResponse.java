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
import java.util.Objects;

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

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null || getClass() != object.getClass()) return false;
        ArtistResponse response = (ArtistResponse) object;
        return popularity == response.getPopularity() &&
                externalUrl.equals(response.getExternalUrl()) &&
                followers.equals(response.getFollowers()) &&
                href.equals(response.getHref()) &&
                id.equals(response.getId()) &&
                images.equals(response.getImages()) &&
                type.equals(response.getType()) &&
                uri.equals(response.getUri());
    }

    @Override
    public int hashCode(){
        return Objects.hash(id,href,name);
    }
}
