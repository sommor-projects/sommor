package com.sommor.extensibility.callback;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@FunctionalInterface
public interface P1VCallback<Ext> extends P1RCallback<Ext, Void> {
    void accept(Ext ext);

    @Override
    default Void apply(Ext ext) {
        accept(ext);
        return null;
    }
}
