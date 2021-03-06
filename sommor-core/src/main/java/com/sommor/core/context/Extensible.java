package com.sommor.core.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/18
 */
public class Extensible implements IExtensible {

    private Map<String, Object> map;

    public Extensible() {
        this.map = new HashMap<>();
    }

    public Extensible(Extensible extensible) {
        this.map = extensible.map;
    }

    public <T> void addExt(T ext) {
        this.addExt(ext.getClass().getName(), ext);
    }

    public <T> void addExt(String key, T ext) {
        this.map.put(key, ext);
    }

    public <T> void addExt(Class clazz, T ext) {
        this.map.put(clazz.getName(), ext);
    }

    public <T> T getExt(Class<T> clazz) {
        return getExt(clazz.getName());
    }

    public <T> T getExt(String key) {
        return (T) map.get(key);
    }

    public void extend(Extensible extensible) {
        this.map.putAll(extensible.map);
    }
}
