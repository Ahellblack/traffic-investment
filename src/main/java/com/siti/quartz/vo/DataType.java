package com.siti.quartz.vo;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by quyue1205 on 2019/1/23.
 */
public final class DataType {

    public static Map<String, String> dateTypeMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Short", "java.lang.Short");
        map.put("Long", "java.lang.Long");
        map.put("Integer", "java.lang.Integer");
        map.put("Float", "java.lang.Float");
        map.put("Double", "java.lang.Double");
        map.put("String", "java.lang.String");
        return map;
    }
}
