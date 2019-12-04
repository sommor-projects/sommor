package com.sommor.mybatis.sql.field.type;

import com.alibaba.fastjson.JSON;
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

    private Map<String, String> rawMap = new HashMap<>();

    private Map<Class, Object> resolvedMap = new HashMap<>();

    public <T> T get(Class<T> clazz) {
        T v = (T) resolvedMap.get(clazz);

        if (null == v) {
            String key = parseConfigKey(clazz);
            String raw = rawMap.get(key);
            if (StringUtils.isNoneBlank(raw)) {
                v = JSONObject.parseObject(raw, clazz);
                resolvedMap.put(clazz, v);
            }
        }

        return v;
    }

    public <T> List<T> getList(Class<T> clazz) {
        List<T> v = (List<T>) resolvedMap.get(clazz);

        if (null == v) {
            String key = parseConfigKey(clazz);
            String raw = rawMap.get(key);
            if (StringUtils.isNoneBlank(raw)) {
                v = JSON.parseArray(raw, clazz);
                resolvedMap.put(clazz, v);
            }
        }

        return v == null ? Collections.emptyList() : v;
    }

    private String parseConfigKey(Class clazz) {
        ConfigKey configKey = (ConfigKey) clazz.getAnnotation(ConfigKey.class);
        if (null != configKey) {
            return configKey.value();
        }

        return clazz.getSimpleName();
    }

    public <T> void add(T t) {
        if (null != t) {
            Class clazz;
            if (t instanceof List) {
                List list = (List) t;
                if (list.isEmpty()) {
                    return;
                }
                clazz = ((List) t).get(0).getClass();
            } else {
                clazz = t.getClass();
            }
            String key = parseConfigKey(clazz);
            resolvedMap.put(clazz, t);
        }
    }

    public String toPersistence() {
        Map<String, String> rawMap = this.rawMap;
        for (Map.Entry<Class, Object> entry : resolvedMap.entrySet()) {
            rawMap.put(parseConfigKey(entry.getKey()), JSON.toJSONString(entry.getValue()));
        }
        return MapUtils.isEmpty(rawMap) ? "" : JSON.toJSONString(rawMap);
    }

    public static Config from(String persistence) {
        Config config = new Config();

        if (StringUtils.isNoneBlank(persistence)) {
            JSONObject jo = JSONObject.parseObject(persistence);
            for (Map.Entry<String, Object> e : jo.entrySet()) {
                config.rawMap.put(e.getKey(), e.getValue().toString());
            }
        }

        return config;
    }

    public Config merge(Config config) {
        this.rawMap.putAll(config.rawMap);
        this.resolvedMap.putAll(config.resolvedMap);

        return this;
    }

    public Map<String, Object> getValues() {
        return this.resolvedMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        p -> parseConfigKey(p.getKey()),
                        p-> p.getValue()
                ));
    }

}
