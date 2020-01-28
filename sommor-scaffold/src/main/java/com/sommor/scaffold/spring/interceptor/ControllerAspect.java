package com.sommor.scaffold.spring.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

/**
 * @author wuyu
 * @since 2019/2/5
 */
@Component
@Aspect
public class ControllerAspect {

    @Autowired
    private List<ControllerInterceptor> handlers;

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        int sleepTime = new Random(System.currentTimeMillis()).nextInt(1000);
        if (sleepTime > 0) {
            Thread.sleep(sleepTime);
        }
        Object returnValue = null;
        Throwable exception = null;

        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        Object[] args = joinPoint.getArgs();
        Invocation invocation = new Invocation(joinPoint.getTarget(), method, args);

        try {
            for (ControllerInterceptor handler : handlers) {
                handler.before(invocation);
            }

            returnValue = joinPoint.proceed();
            return returnValue;
        } catch (Throwable e) {
            exception = e;
            throw e;
        } finally {
            InvocationResult invocationResult = new InvocationResult(returnValue, exception);
            for (ControllerInterceptor handler : handlers) {
                handler.after(invocation, invocationResult);
            }
        }
    }
}
