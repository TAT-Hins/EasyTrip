package com.seu.cose.easytrip.Override;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by Hins on 2017/9/11,011.
 */

public class GsonPaserUtils {

    private Gson gson;

    public GsonPaserUtils() {
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    public String formateToJson(final Object obj){
        if(obj != null){
            return this.gson.toJson(obj);
        }else{
            return null;
        }
    }

    public Object convertToObj(final String json, Class<?> obj){
        if(json != null){
            return this.gson.fromJson(json, obj);
        }else{
            return null;
        }
    }
    public Type convertToObj(final String json, Type type){
        if(json != null){
            return this.gson.fromJson(json, type);
        }else{
            return null;
        }
    }

}
