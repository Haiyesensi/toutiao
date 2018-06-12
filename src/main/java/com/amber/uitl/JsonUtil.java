package com.amber.uitl;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class JsonUtil {

    public static String getJSONString(int code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        return jsonObject.toJSONString();
    }

    public static String getJSONString(int code,String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        return jsonObject.toJSONString();
    }

    public static String getJSONString(int code, Map<String,Object>map){
        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String,Object> entry : map.entrySet()){
            jsonObject.put(entry.getKey(),entry.getValue());
        }
        return jsonObject.toJSONString();
    }
}
