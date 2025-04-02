package com.github.gmarcg00.spotify.data.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExternalUrl {

    private String spotify;

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null || getClass() != object.getClass()) return false;
        ExternalUrl externalUrl = (ExternalUrl) object;
        return Objects.equals(spotify,externalUrl.getSpotify());
    }

    @Override
    public int hashCode(){
        return Objects.hash(spotify);
    }

}
