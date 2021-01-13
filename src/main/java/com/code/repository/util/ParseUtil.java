package com.code.repository.util;

import java.util.HashMap;
import java.util.Map;

public class ParseUtil {
    public static Map<String,Object> parseMapByQueryString(String queryString){
        Map<String,Object> map = new HashMap<>();
        if (StringUtil.isNotNull(queryString)) {
            String[] params = queryString.split("&");
            for(String param : params){
                String[] p = param.split("=");
                map.put(p[0],p[1]);
            }
        }
        return map;
    }
}
