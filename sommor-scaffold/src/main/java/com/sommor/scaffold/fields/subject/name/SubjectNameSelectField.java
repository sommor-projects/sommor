package com.sommor.scaffold.fields.subject.name;

import com.sommor.scaffold.view.SelectView;
import com.sommor.scaffold.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldConfig(SelectView.class)
public @interface SubjectNameSelectField {

    String title() default "内容主体";

}
