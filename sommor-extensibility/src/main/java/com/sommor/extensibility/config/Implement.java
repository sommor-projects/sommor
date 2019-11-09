package com.sommor.extensibility.config;

import com.sommor.extensibility.Priorities;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Implement {

    int priority() default Priorities.DEFAULT;
}
