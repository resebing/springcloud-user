package com.imooc.order.server.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    static ObjectMapper objectMapper = new ObjectMapper();


    public static Object fromJson(String str, TypeReference typeReference) {
        try {
            return objectMapper.readValue(str, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
