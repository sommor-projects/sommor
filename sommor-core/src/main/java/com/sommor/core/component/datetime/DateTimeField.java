package com.sommor.core.component.datetime;

import com.sommor.core.model.config.TargetAnnotation;
import com.sommor.core.utils.DateTimeUtil;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(DateTimeFieldConfig.class)
public @interface DateTimeField {

    String format() default DateTimeUtil.DATE_TIME_FORMAT;

}
