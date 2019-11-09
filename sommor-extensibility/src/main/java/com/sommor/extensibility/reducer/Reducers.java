package com.sommor.extensibility.reducer;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author wuyu
 * @since 2018/7/21
 */
public class Reducers {
    public static <R> Reducer<R, Boolean> anyMatch(Predicate<R> predicate) {
        return new AnyMatch<>(predicate);
    }

    public static <R> Reducer<R, Boolean> allMatch(Predicate<R> predicate) {
        return new AllMatch<>(predicate);
    }

    public static <R> Reducer<R, R> first() {
        return new First<>();
    }

    public static <R> Reducer<R, R> firstNotNull() {
        return new FirstNotNull<>();
    }

    public static <R> Reducer<R, List<R>> collect() {
        return new Collect<>();
    }

    public static <R> Reducer<R, List<R>> collectNotNull() {
        return new CollectNotNull<>();
    }

    public static <K, V> MapFlat<K, V> mapFlat() {
        return new MapFlat<>();
    }

    public static <V> ListFlat<V> listFlat() {
        return new ListFlat<>();
    }

    public static <V> SetFlat<V> setFlat() {
        return new SetFlat<>();
    }
}
