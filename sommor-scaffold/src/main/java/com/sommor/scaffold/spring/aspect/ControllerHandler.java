package com.sommor.scaffold.spring.aspect;


/**
 * @author wuyu
 * @since 2019/2/5
 */
public interface ControllerHandler {

    default void before(Invocation invocation) {
    }

    default void after(Invocation invocation, InvocationResult invocationResult) {
    }
}
