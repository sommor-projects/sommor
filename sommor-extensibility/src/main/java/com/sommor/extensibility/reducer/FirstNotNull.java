package com.sommor.extensibility.reducer;

import com.sommor.extensibility.Implementor;

/**
 * @author wuyu
 * @since 2018/8/1
 */
public class FirstNotNull<R> extends Reducer<R, R> {
    @Override
    public boolean reduce(R r, Implementor implementor) {
        if (null != r) {
            this.setResult(r);
            return true;
        }

        return false;
    }
}