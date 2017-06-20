package com.zsjx.store.homepage.lib.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.lang.reflect.Type;

public class DataUtil {
    public static Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * 使用单一的Json解析入口可以方便更换解析工具
     *
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    /**
     * 如果解析失败，返回默认的值
     *
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> tClass, T def) {
        try {
            return fromJson(json, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return def;
        }
    }

    public static <T> T fromJson(String json, Type typeOfT, T def) {
        try {
            return fromJson(json, typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
            return def;
        }
    }

    /**
     * @param json
     * @param typeOfT use new TypeToken<ArrayList<T>>() {}.getType()
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static String toJson(Serializable obj) {
        return gson.toJson(obj);
    }
}
