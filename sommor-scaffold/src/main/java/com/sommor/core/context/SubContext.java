package com.sommor.core.context;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/29
 */
public class SubContext extends Context {

    private Context mainContext;

    public SubContext(Context mainContext) {
        super();
        this.mainContext = mainContext;
    }

    @Override
    public <T> T getExt(Class<T> clazz) {
        T t = super.getExt(clazz);
        if (null != t) {
            return t;
        }

        return mainContext.getExt(clazz);
    }

    @Override
    public <T> T getExt(String key) {
        T t = super.getExt(key);
        if (null != t) {
            return t;
        }

        return mainContext.getExt(key);
    }
}
