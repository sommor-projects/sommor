package com.sommor.component.configurable;

import com.sommor.component.form.FieldSaveContext;
import com.sommor.component.form.extension.FormFieldSavingInterceptor;
import com.sommor.extensibility.config.Implement;
import com.sommor.model.FieldDefinition;
import com.sommor.model.ModelField;
import com.sommor.model.ReflectModelField;
import com.sommor.model.define.FieldDefineInterceptor;
import com.sommor.model.fill.FieldFillContext;
import com.sommor.model.fill.FieldFillInterceptor;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.sql.field.type.Config;
import com.sommor.mybatis.sql.field.type.ConfigKey;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
@Implement
public class ConfigurableFieldInterceptor implements
        FieldDefineInterceptor,
        FieldFillInterceptor,
        FormFieldSavingInterceptor {

    @Override
    public void interceptOnFieldDefine(FieldDefinition fieldDefinition) {
        ConfigurableFieldConfig cfc = null;

        ConfigKey configKey = fieldDefinition.getAnnotation(ConfigKey.class);
        if (null != configKey) {
            String key = configKey.value().isEmpty() ? fieldDefinition.getName() : configKey.value();
            cfc = new ConfigurableFieldConfig();
            cfc.setKey(key);
        } else {
            if (fieldDefinition.getSubModelDefinition() != null) {
                Class clazz =  fieldDefinition.getSubModelDefinition().getModelClass();
                if (Void.class != clazz && clazz.getAnnotation(ConfigKey.class) != null) {
                    cfc = new ConfigurableFieldConfig();
                    cfc.setKey(Config.parseConfigKey(clazz));
                }
            }
        }

        if (null != cfc) {
            fieldDefinition.addExt(cfc);
        }
    }

    @Override
    public Object interceptOnFieldFill(FieldFillContext ctx) {
        ConfigurableFieldConfig cfc = ctx.getModelField().getExt(ConfigurableFieldConfig.class);
        if (null != cfc) {
            String key = cfc.getKey();
            Config config = cfc.getConfig();
            if (null == config) {
                config = ctx.getSourceModel().getFieldValue("config");
            }

            if (null != config) {
                ModelField modelField = ctx.getModelField();
                Class fieldType = modelField.getType();
                if (fieldType.equals(List.class)) {
                    if (modelField instanceof ReflectModelField) {
                        Class type = (Class) ((ParameterizedType) ((ReflectModelField) modelField).getReflectField().getGenericType()).getActualTypeArguments()[0];
                        return config.getList(key, type);
                    }
                } else {
                    return config.get(key, fieldType);
                }
            }
        }
        return null;
    }

    @Override
    public void interceptOnFormSaving(FieldSaveContext ctx) {
        ConfigurableFieldConfig cfc = ctx.getModelField().getExt(ConfigurableFieldConfig.class);
        if (null != cfc) {
            Object v = ctx.getFieldValue();
            BaseEntity baseEntity = ctx.getEntity();
            if (v != null && baseEntity instanceof ConfigurableEntity) {
                ((ConfigurableEntity) baseEntity).addConfig(cfc.getKey(), v);
            }
        }
    }
}
