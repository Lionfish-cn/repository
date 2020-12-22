package com.electric.business.util;

import com.alibaba.fastjson.JSONObject;

public class ResponseUtil {

    public static String loginResponseOk(String message,String token){
        JSONObject o = new JSONObject();
        o.put("code",0);
        JSONObject body = new JSONObject();
        body.put("token",token);
        o.put("data",body);
        o.put("message",message);
        o.put("statusCode",200);
        return o.toJSONString();
    }


    public static String filterResponseError(String token){
        JSONObject o = new JSONObject();
        o.put("code",0);
        JSONObject body = new JSONObject();
        body.put("token",token);
        o.put("data",body);
        o.put("message","The token Expire ...");
        o.put("statusCode",401);
        return o.toJSONString();
    }


}
