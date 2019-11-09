package com.sommor.extensibility.reducer;

import com.sommor.extensibility.Implementor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wuyu
 * @since 2018/8/1
 */
public class SetFlat<V> extends Reducer<Set<V>, Set<V>> {
    public SetFlat() {
        this.setResult(new HashSet<>());
    }

    @Override
    public boolean reduce(Set<V> set, Implementor implementor) {
        if (null != set) {
            this.getResult().addAll(set);
        }

        return false;
    }
}
