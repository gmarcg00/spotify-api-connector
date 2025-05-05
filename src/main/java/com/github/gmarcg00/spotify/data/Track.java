package com.github.gmarcg00.spotify.data;

import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Track {
    private Album album;
    private List<Artist> artists;
    private List<String> availableMarkets;
    private int discNumber;
    private long duration;
    private boolean explicit;
    private ExternalUrl externalUrl;
    private String href;
    private String id;
    private boolean isLocal;
    private String name;
    private int popularity;
    private int trackNumber;
    private String type;
    private String uri;
}
