package com.sommor.core.spring.interceptor;


/**
 * @author wuyu
 * @since 2019/2/5
 */
public interface ControllerInterceptor {

    default void before(Invocation invocation) {
    }

    default void after(Invocation invocation, InvocationResult invocationResult) {
    }
}
