package com.sommor.spring.aspect;

import java.lang.reflect.Method;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class Invocation {
    private Method method;

    private Object[] arguments;

    public Invocation(Method method, Object[] arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }
}
