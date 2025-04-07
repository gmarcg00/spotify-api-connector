package com.github.gmarcg00.spotify.utils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestHelper {

    private static final String IDS_PARAM = "ids";

    public static void assertNotNullFields(Object object){
        if(object == null) return;
        Class<?> clazz = object.getClass();
        for(Field field: clazz.getDeclaredFields()){
            field.setAccessible(true);
            try{
                Object value = field.get(object);
                assertNotNull(value);
            }catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String buildSimpleGetListUri(String domain, String [] ids){
        String path = domain.concat("?".concat(IDS_PARAM).concat("="));
        String idsParam = String.join(",",ids);
        return path.concat(idsParam);
    }
}
