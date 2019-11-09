package com.sommor.extensibility.reducer;

import com.sommor.extensibility.Implementor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuyu
 * @since 2018/8/1
 */
public class MapFlat<K, V> extends Reducer<Map<K, V>, Map<K, V>> {

    public MapFlat() {
        this.setResult(new HashMap<K, V>());
    }

    @Override
    public boolean reduce(Map<K, V> map, Implementor implementor) {
        if (null != map) {
            this.getResult().putAll(map);
        }

        return false;
    }
}
