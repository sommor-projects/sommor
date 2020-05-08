package com.sommor.core.component.bytes;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/3
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(BytesConvertConfig.class)
public @interface BytesConvert {

    String field() default "";

    boolean autoUnit() default false;

    ByteUnit sourceUnit() default ByteUnit.B;

    ByteUnit targetUnit() default ByteUnit.GB;

}
