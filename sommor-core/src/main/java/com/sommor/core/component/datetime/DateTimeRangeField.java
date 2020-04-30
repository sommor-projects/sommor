package com.sommor.core.component.datetime;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(DateTimeRangeFieldConfig.class)
public @interface DateTimeRangeField {

    String title();

    String startTimeFieldName() default "startTime";

    String endTimeFieldName() default "endTime";

    String format() default "YYYY-MM-DD";

}
