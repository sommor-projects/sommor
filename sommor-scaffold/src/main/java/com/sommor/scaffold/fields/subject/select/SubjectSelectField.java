package com.sommor.scaffold.fields.subject.select;

import com.sommor.core.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldConfig(SubjectSelectView.class)
public @interface SubjectSelectField {

    String title();

    String subject();

    String entityTitleName() default "title";

    String[] features() default {};

}
