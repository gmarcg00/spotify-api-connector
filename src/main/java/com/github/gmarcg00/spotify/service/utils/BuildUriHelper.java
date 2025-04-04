package com.github.gmarcg00.spotify.service.utils;

public class BuildUriHelper {

    private static final String IDS_PARAM = "ids";

    private BuildUriHelper(){}

    public static String buildSimpleGetListUri(String domain, String [] ids){
        String path = domain.concat("?".concat(IDS_PARAM).concat("="));
        String idsParam = String.join(",",ids);
        return path.concat(idsParam);
    }
}
