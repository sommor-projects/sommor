package com.sommor.scaffold.view.field;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.scaffold.context.RequestContext;
import com.sommor.scaffold.view.field.action.FormAction;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class Form extends Fieldset {

    @Getter
    @Setter
    private FormAction action;

    private Map<String, FieldContext> fieldContextMap = new HashMap<>();

    public Form(Object target) {
        super(target);
    }

    public void validate(FormValidateContext formValidateContext) {
        for (String fieldName: targetData.getFieldNames()) {
            FieldDefinition fd = targetData.getField(fieldName);

            if (null != fd.getFieldConfig()) {
                FieldContext ctx = new FieldContext(RequestContext.getSubContext(fd));
                ctx.setDefinition(fd);
                ctx.setData(this.targetData);

                ExtensionExecutor.of(FieldProcessor.class).run(
                        fd.getFieldConfig(),
                        ext -> ext.processOnFormValidate(fd.getFieldConfig(), ctx)
                );
            }
        }
    }

    public void saving(BaseEntity entity, BaseEntity originalEntity) {
        for (String fieldName: targetData.getFieldNames()) {
            FieldDefinition fd = targetData.getField(fieldName);

            FieldSaveContext ctx = new FieldSaveContext(RequestContext.getSubContext(fd));
            ctx.setDefinition(fd);
            ctx.setData(targetData);
            ctx.setEntity(entity);
            ctx.setOriginal(originalEntity);

            if (null != fd.getFieldConfig()) {

                this.fieldProcessor.run(
                        fd.getFieldConfig(),
                        ext -> ext.processOnFormSaving(fd.getFieldConfig(), ctx)
                );
            }

            this.fieldInterceptor.run(
                    ext -> ext.interceptOnFormSaving(ctx)
            );
        }
    }

    public void saved(BaseEntity entity, BaseEntity originalEntity) {
        for (String fieldName: targetData.getFieldNames()) {
            FieldDefinition fd = targetData.getField(fieldName);

            FieldSaveContext ctx = new FieldSaveContext(RequestContext.getSubContext(fd));
            ctx.setDefinition(fd);
            ctx.setData(targetData);
            ctx.setEntity(entity);
            ctx.setOriginal(originalEntity);

            if (null != fd.getFieldConfig()) {
                ExtensionExecutor.of(FieldProcessor.class).run(
                        fd.getFieldConfig(),
                        ext -> ext.processOnFormSaved(fd.getFieldConfig(), ctx)
                );
            }

        }
    }
}
