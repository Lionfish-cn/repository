package com.code.repository.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

    public static Map<String,Object> getQueryString(HttpServletRequest request){
        Map<String,Object> m = new HashMap<>();
        Enumeration<String> enums = request.getParameterNames();
        while(enums.hasMoreElements()){
            String key = enums.nextElement();
            String value = request.getParameter(key);
            if(StringUtil.isNotNull(value)){
              m.put(key,value);
            }
        }
        return m;
    }

    public static String getPayloadParams(HttpServletRequest request){
        try{
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = request.getReader();
            char[] buff = new char[1024];
            int i = 0;
            while((i=reader.read())!=-1){
                sb.append(buff,0,i);
            }
            return sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String,Object> getHeaders(HttpServletRequest request){
        Enumeration<String> headers = request.getHeaderNames();

        Map<String,Object> m = new HashMap<>();
        while(headers.hasMoreElements()) {
            String key = headers.nextElement();
            String value = request.getHeader(key);
            m.put(key,value);
        }
        return m;
    }

}
