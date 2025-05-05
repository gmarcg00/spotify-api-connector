package com.github.gmarcg00.spotify.data.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Follower {
    private String href;
    private Integer total;

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null || getClass() != object.getClass()) return false;
        Follower follower = (Follower) object;
        return Objects.equals(total,follower.total);
    }

    @Override
    public int hashCode(){
        return Objects.hash(total);
    }
}
