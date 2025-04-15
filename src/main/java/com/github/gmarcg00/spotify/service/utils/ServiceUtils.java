package com.github.gmarcg00.spotify.service.utils;

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
}
