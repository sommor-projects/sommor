package com.sommor.mybatis.sql.field.type;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfigKey {
    String value();
}
