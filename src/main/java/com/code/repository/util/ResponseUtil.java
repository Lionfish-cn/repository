package com.code.repository.util;


import net.sf.json.JSONObject;

public class ResponseUtil {

    public static String responseOk(String message, JSONObject object){
        JSONObject o = new JSONObject();
        o.put("code",0);
        JSONObject body = new JSONObject();
        if(object!=null){
            body.put("body",object);
        }
        o.put("data",body);
        o.put("message",message);
        o.put("statusCode",200);
        return o.toString();
    }


    public static String responseError(String message,JSONObject object){
        JSONObject o = new JSONObject();
        o.put("code",1);
        JSONObject body = new JSONObject();
        if(object!=null){
            body.put("body",object);
        }
        o.put("data",body);
        o.put("message",message);
        o.put("statusCode",401);
        return o.toString();
    }


}
