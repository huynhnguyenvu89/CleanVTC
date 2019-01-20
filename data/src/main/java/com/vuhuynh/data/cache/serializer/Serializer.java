package com.vuhuynh.data.cache.serializer;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Json Serializer/Deserializer
 */
@Singleton
public class Serializer {

    private final Gson gson = new Gson();

    @Inject
    Serializer(){}

    /**
     * Deserialize a json representation of an Object.
     *
     * @param string
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T deserialize(String string, Class<T> clazz){
        return gson.fromJson(string, clazz);
    }

    /**
     * Serialize an Object to Json String.
     *
     * @param object
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> String serialize(Object object, Class<T> clazz) {
        return gson.toJson(object, clazz);
    }
}
