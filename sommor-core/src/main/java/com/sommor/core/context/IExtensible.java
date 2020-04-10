package com.sommor.core.context;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public interface IExtensible {

    <T> void addExt(T ext);

    <T> void addExt(String key, T ext);

    <T> void addExt(Class clazz, T ext);

    <T> T getExt(Class<T> clazz);

    <T> T getExt(String key);
}
