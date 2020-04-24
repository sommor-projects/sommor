package com.sommor.bundles.taxonomy.component.attribute;

import com.sommor.core.utils.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
public class Attributes {

    private Map<String, Object> map;

    public Attributes(Map<String, Object> map) {
        this.map = map;
    }

    public Attributes() {
        this.map = new HashMap<>();
    }

    public String getString(String key) {
        return Converter.toString(map.get(key));
    }

    public Integer getInteger(String key) {
        return Converter.parseInt(map.get(key));
    }

    public Long getLong(String key) {
        return Converter.parseLong(map.get(key));
    }
}
