package com.sommor.scaffold.fields.subject.count;

import com.sommor.core.view.TextView;
import com.sommor.core.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldConfig(TextView.class)
public @interface SubjectCountField {

    String title();

    String subject();

    String subjectGroupFieldName();
}
