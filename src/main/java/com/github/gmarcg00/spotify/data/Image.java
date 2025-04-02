package com.github.gmarcg00.spotify.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Image {
    private String url;
    private Integer height;
    private Integer width;

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null || getClass() != object.getClass()) return false;
        Image image = (Image) object;
        return Objects.equals(url,image.getUrl()) &&
                Objects.equals(height,image.getHeight()) &&
                Objects.equals(width,image.getWidth());
    }

    @Override
    public int hashCode(){
        return Objects.hash(url,height,width);
    }
}
