package com.shooterj.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/11/2
 */
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 获取ObjectMapper
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * 对象转换为JsonNode
     *
     * @param obj 对象
     * @return JsonNode
     * @throws IOException
     */
    public static JsonNode toJsonNode(Object obj) {
        Objects.requireNonNull(obj, "obj can not be empty.");
        return mapper.convertValue(obj, JsonNode.class);
    }

    /**
     * 字符串转JsonNode
     *
     * @param json 字符串
     * @return JsonNode
     * @throws IOException
     */
    public static JsonNode toJsonNode(String json) throws IOException {
        return mapper.readTree(json);
    }


}
