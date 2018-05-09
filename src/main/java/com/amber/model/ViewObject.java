package com.amber.model;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {
    private Map<String,Object> map = new HashMap<String, Object>();
    public void set(String s,Object o){
        map.put(s,o);
    }
    public Object get(String k){
        return map.get(k);
    }
}
