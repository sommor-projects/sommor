package com.sommor.extensibility.reducer;

import com.sommor.extensibility.Implementor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyu
 * @since 2018/7/21
 */
public class Collect<Result> extends Reducer<Result, List<Result>> {
    public Collect() {
        this.setResult(new ArrayList<>());
    }

    @Override
    public boolean reduce(Result result, Implementor implementor) {
        this.getResult().add(result);
        return false;
    }
}
