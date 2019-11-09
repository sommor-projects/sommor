package com.sommor.view.config;

import com.sommor.view.HiddenInputView;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldView(HiddenInputView.class)
@Documented
public @interface HiddenInput {
}
