package com.sommor.view.config;

import com.sommor.view.SelectView;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldView(SelectView.class)
public @interface Select {
}
