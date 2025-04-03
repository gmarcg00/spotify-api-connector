package com.github.gmarcg00.spotify.external.api.mapper;

import com.github.gmarcg00.spotify.data.Episode;
import com.github.gmarcg00.spotify.external.api.model.response.episode.EpisodeResponse;

public class EpisodeMapper {

    private EpisodeMapper(){}

    public static Episode toEntity(EpisodeResponse response){
        Episode.Podcast podcast = toEntity(response.getShow());
        return Episode.builder()
                .audioPreviewUrl(response.getAudioPreviewUrl())
                .description(response.getDescription())
                .duration(response.getDuration())
                .explicit(response.isExplicit())
                .externalUrl(response.getExternalUrl())
                .href(response.getHref())
                .htmlDescription(response.getHtmlDescription())
                .id(response.getId())
                .images(response.getImages())
                .isExternallyHosted(response.isExternallyHosted())
                .isPlayable(response.isPlayable())
                .language(response.getLanguage())
                .languages(response.getLanguages())
                .name(response.getName())
                .releaseDate(response.getReleaseDate().toInstant())
                .podcast(podcast)
                .type(response.getType())
                .uri(response.getUri())
                .build();
    }

    public static Episode.Podcast toEntity(EpisodeResponse.PodcastInfo podcastInfo){
        return Episode.Podcast.builder()
                .availableMarkets(podcastInfo.getAvailableMarkets())
                .description(podcastInfo.getDescription())
                .explicit(podcastInfo.isExplicit())
                .externalUrl(podcastInfo.getExternalUrl())
                .href(podcastInfo.getHref())
                .htmlDescription(podcastInfo.getHtmlDescription())
                .id(podcastInfo.getId())
                .images(podcastInfo.getImages())
                .isExternallyHosted(podcastInfo.isExternallyHosted())
                .languages(podcastInfo.getLanguages())
                .mediaType(podcastInfo.getMediaType())
                .name(podcastInfo.getName())
                .publisher(podcastInfo.getPublisher())
                .totalEpisodes(podcastInfo.getTotalEpisodes())
                .type(podcastInfo.getType())
                .uri(podcastInfo.getUri())
                .build();
    }
}
