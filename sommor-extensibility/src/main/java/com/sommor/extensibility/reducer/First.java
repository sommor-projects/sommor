package com.sommor.extensibility.reducer;

import com.sommor.extensibility.Implementor;

/**
 * @author wuyu
 * @since 2018/7/21
 */
public class First<R> extends Reducer<R, R> {
    @Override
    public boolean reduce(R element, Implementor implementor) {
        this.setResult(element);
        return true;
    }
}
