package com.electric.business.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class RequestUtil {

    public static String getQueryString(HttpServletRequest request){
        String queryString = "";
        Enumeration<String> enums = request.getParameterNames();
        while(enums.hasMoreElements()){
            String key = enums.nextElement();
            String value = request.getParameter(key);
            if(StringUtil.isNotNull(value)){
                if(StringUtil.isNotNull(queryString)){
                    queryString = queryString + "&" + key +"="+value;
                }else{
                    queryString = key +"="+value;
                }
            }
        }
        return queryString;
    }
}
