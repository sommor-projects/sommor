package com.sommor.extensibility.reducer;

import com.sommor.extensibility.Implementor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyu
 * @since 2018/8/1
 */
public class ListFlat<V> extends Reducer<List<V>, List<V>> {
    public ListFlat() {
        this.setResult(new ArrayList<V>());
    }

    @Override
    public boolean reduce(List<V> list, Implementor implementor) {
        if (null != list) {
            this.getResult().addAll(list);
        }

        return false;
    }
}
