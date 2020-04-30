package com.sommor.bundles.taxonomy.component.attribute;

import com.sommor.core.utils.Converter;
import com.sommor.core.utils.ProxyMap;

import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
public class Attributes extends ProxyMap {

    public Attributes(Map<String, Object> map) {
        super(map);
    }

    public Attributes() {
        super();
    }

    public String getString(String key) {
        return Converter.toString(this.get(key));
    }

    public Integer getInteger(String key) {
        return Converter.parseInt(this.get(key));
    }

    public Long getLong(String key) {
        return Converter.parseLong(this.get(key));
    }
}
