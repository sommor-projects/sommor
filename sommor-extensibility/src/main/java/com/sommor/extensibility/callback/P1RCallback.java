package com.sommor.extensibility.callback;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@FunctionalInterface
public interface P1RCallback<Ext, R> {
    R apply(Ext ext);
}
