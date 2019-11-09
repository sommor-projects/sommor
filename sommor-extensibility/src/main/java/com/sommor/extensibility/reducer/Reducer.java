package com.sommor.extensibility.reducer;

import com.sommor.extensibility.Implementor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public abstract class Reducer<Result, ReducedResult> {
    private ReducedResult result;

    public ReducedResult getResult() {
        return result;
    }

    protected void setResult(ReducedResult result) {
        this.result = result;
    }

    public abstract boolean reduce(Result result, Implementor implementor);
}
