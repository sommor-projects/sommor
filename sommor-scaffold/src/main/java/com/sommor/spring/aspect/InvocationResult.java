package com.sommor.spring.aspect;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class InvocationResult {

    private Object returnValue;

    private Throwable exception;

    public InvocationResult(Object returnValue, Throwable exception) {
        this.returnValue = returnValue;
        this.exception = exception;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public Throwable getException() {
        return exception;
    }
}
