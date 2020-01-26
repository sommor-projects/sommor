package com.sommor.scaffold.spring.interceptor;

import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class Invocation {

    @Getter
    private Object target;

    @Getter
    private Method method;

    @Getter
    private Object[] arguments;

    public Invocation(Object target, Method method, Object[] arguments) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }
}
