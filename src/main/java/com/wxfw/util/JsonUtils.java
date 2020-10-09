package com.wxfw.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JsonUtils
 *
 * @author gaohw
 * @date 2020/3/17
 */
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonString(Object obj) {
        String value = "";
        try {
            value = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String[] toStringArray(String jsonSource) {
        return new String[]{};
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            T obj = objectMapper.readValue(json, clazz);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toObjectList(String json, Class<T> clazz) {
        try {
            JavaType javaType = getCollectionType(ArrayList.class, clazz);
            List<T> list = objectMapper.readValue(json, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> toMap(String json) {
        return toObject(json, Map.class);
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametrizedType(collectionClass, collectionClass, elementClasses);
    }
}
