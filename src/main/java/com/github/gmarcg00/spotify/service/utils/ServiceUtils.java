package com.github.gmarcg00.spotify.service.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class ServiceUtils {

    private ServiceUtils(){}

    public static String addQueryParams(String path, String limit, String offset, String albumTypes) {
        StringBuilder queryBuilder = new StringBuilder(path);
        boolean hasQuestionMark = path.contains("?");

        if (limit != null && !limit.isBlank()) {
            queryBuilder.append(hasQuestionMark ? "&" : "?");
            queryBuilder.append("limit=").append(limit);
            hasQuestionMark = true;
        }

        if (offset != null && !offset.isBlank()) {
            queryBuilder.append(hasQuestionMark ? "&" : "?");
            queryBuilder.append("offset=").append(offset);
            hasQuestionMark = true;
        }

        if (albumTypes != null && !albumTypes.isBlank()) {
            queryBuilder.append(hasQuestionMark ? "&" : "?");
            queryBuilder.append("include_groups=").append(albumTypes);
        }
        return queryBuilder.toString();
    }

    public static String addQueryParams(String path, String limit, String offset){
        StringBuilder queryBuilder = new StringBuilder(path);
        boolean hasQuestionMark = path.contains("?");

        if (limit != null && !limit.isBlank()) {
            queryBuilder.append(hasQuestionMark ? "&" : "?");
            queryBuilder.append("limit=").append(limit);
            hasQuestionMark = true;
        }

        if (offset != null && !offset.isBlank()) {
            queryBuilder.append(hasQuestionMark ? "&" : "?");
            queryBuilder.append("offset=").append(offset);
        }

        return queryBuilder.toString();
    }

    public static String[] combine(String[] arr, String value){
        Objects.requireNonNull(arr,"object must not be null");
        return Stream.concat(Arrays.stream(arr), Stream.of(value))
                .toArray(String[]::new);
    }

    public static void checkNullValues(String... values){
        for(String value : values){
            Objects.requireNonNull(value,"object must not be null");
        }
    }
}
