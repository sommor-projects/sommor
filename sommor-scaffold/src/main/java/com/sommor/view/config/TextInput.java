package com.sommor.view.config;

import com.sommor.view.TextInputView;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldView(TextInputView.class)
public @interface TextInput {

}
