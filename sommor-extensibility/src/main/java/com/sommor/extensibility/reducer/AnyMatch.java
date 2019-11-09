package com.sommor.extensibility.reducer;

import com.sommor.extensibility.Implementor;

import java.util.function.Predicate;

/**
 * @author wuyu
 * @since 2018/7/21
 */
public class AnyMatch<T> extends Reducer<T, Boolean> {

    private Predicate<T> predicate;

    public AnyMatch(Predicate<T> predicate) {
        this.predicate = predicate;
        this.setResult(false);
    }

    @Override
    public boolean reduce(T element, Implementor implementor) {
        if (predicate.test(element)) {
            this.setResult(true);
            return true;
        }
        return false;
    }
}
