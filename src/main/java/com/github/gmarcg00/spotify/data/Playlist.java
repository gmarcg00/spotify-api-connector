package com.github.gmarcg00.spotify.data;

import com.github.gmarcg00.spotify.data.other.ExternalUrl;
import com.github.gmarcg00.spotify.data.other.Follower;
import com.github.gmarcg00.spotify.data.other.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Playlist {
    private boolean collaborative;
    private String description;
    private ExternalUrl externalUrl;
    private Follower followers;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private User owner;
    private String primaryColor;
    private boolean isPublic;
    private String snapshotId;
    private List<Track> tracks;
    private List<Episode> episodes;
    private String type;
    private String uri;
}
