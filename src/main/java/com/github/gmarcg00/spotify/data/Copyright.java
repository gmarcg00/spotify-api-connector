package com.github.gmarcg00.spotify.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Copyright {
    private String text;
    private String type;

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null || getClass() != object.getClass()) return false;
        Copyright copyright = (Copyright) object;
        return text.equals(copyright.getText()) &&
                type.equals(copyright.getType());
    }

    @Override
    public int hashCode(){
        return Objects.hash(text,type);
    }
}
