package com.sommor.taxonomy.form;

import com.sommor.view.SelectView;
import com.sommor.view.config.FieldView;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldView(SelectView.class)
@Documented
public @interface TaxonomySelect {

    boolean multiple() default false;

    boolean parentIsSelf() default false;

    int parentId() default -1;

}
