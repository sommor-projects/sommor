package com.sommor.mybatis.sql.field.type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
public class Config {

    private JSONObject json;

    public Config() {
        this.json = new JSONObject();
    }

    public Config(String jsonString) {
        if (StringUtils.isNoneBlank(jsonString)) {
            JSONObject json = JSONObject.parseObject(jsonString);
            this.json = json;
        }

    }

    public String getString(String key) {
        return get(key, String.class);
    }

    public Long getLong(String key) {
        return get(key, Long.class);
    }

    public <V> V get(String key, Class<V> type) {
        return json.getObject(key, type);
    }

    public <T> T get(Class<T> clazz) {
        String key = parseConfigKey(clazz);
        if (json.containsKey(key)) {
            return json.getObject(key, clazz);
        }
        return null;
    }

    public <T> List<T> getList(Class<T> clazz) {
        String key = parseConfigKey(clazz);
        return this.getList(key, clazz);
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        if (json.containsKey(key)) {
            JSONArray jsonArray = json.getJSONArray(key);
            if (null != jsonArray) {
                return jsonArray.toJavaList(clazz);
            }
        }

        return null;
    }

    public static String parseConfigKey(Class clazz) {
        ConfigKey configKey = (ConfigKey) clazz.getAnnotation(ConfigKey.class);
        String key = clazz.getSimpleName();
        if (null != configKey && ! configKey.value().isEmpty()) {
            key = configKey.value();
        }
        return key;
    }

    public <T> void add(T t) {
        String key = parseConfigKey(t.getClass());
        json.put(key, t);
    }

    public void add(String key, Object value) {
        json.put(key, value);
    }

    public String toPersistence() {
        if (json.isEmpty()) {
            return "";
        }

        return json.toJSONString();
    }

    public static Config from(String persistence) {
        return new Config(persistence);
    }

    public Config merge(Config config) {
        this.json.putAll(config.json);
        return this;
    }
}
