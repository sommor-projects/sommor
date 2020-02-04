package com.sommor.bundles.user.fields.gender;

import com.sommor.core.view.SelectView;
import com.sommor.core.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldConfig(SelectView.class)
@Documented
public @interface GenderField {

    String title() default  "性别";

}
