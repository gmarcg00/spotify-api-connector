package com.github.gmarcg00.spotify.data;

import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {
    private String displayName;
    private ExternalUrl externalUrl;
    private String href;
    private String id;
    private String type;
    private String uri;
}
