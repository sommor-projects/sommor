package com.sommor.component.form.extension;

import com.sommor.component.form.FieldSaveContext;
import com.sommor.curd.query.FieldContext;
import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Extension(name = "表单字段检验处理器")
public interface FormFieldValidateProcessor<FA> {
    void processOnFormValidate(FA fa, FieldContext ctx);
}
