package com.sommor.scaffold.entity.configurable;

import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.sql.field.type.Config;
import com.sommor.mybatis.sql.field.type.ConfigKey;
import com.sommor.scaffold.view.field.FieldContext;
import com.sommor.scaffold.view.field.FieldInterceptor;
import com.sommor.scaffold.view.field.FieldSaveContext;
import com.sommor.scaffold.view.field.FieldDefinition;
import com.sommor.scaffold.view.field.FieldsetDefinition;
import com.sommor.scaffold.view.field.config.FieldsetConfig;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Implement
public class ConfigurableFieldInterceptor implements FieldInterceptor {

    private static final String KEY = "configurableKey";

    @Override
    public void interceptOnInit(FieldDefinition definition) {
        ConfigKey key = definition.getField().getAnnotation(ConfigKey.class);
        if (null != key) {
            definition.addExt(KEY, key.value().isEmpty() ? definition.getName() : key.value());
        }

        if (definition instanceof FieldsetDefinition) {
            Class clazz =  ((FieldsetConfig) definition.getFieldConfig()).value();
            if (Void.class != clazz && clazz.getAnnotation(ConfigKey.class) != null) {
                definition.addExt(KEY, Config.parseConfigKey(clazz));
            }
        }
    }

    @Override
    public Object interceptOnFill(FieldContext ctx) {
        Object target = ctx.getData().getTarget();
        if (target instanceof ConfigurableEntity) {
            ConfigurableEntity entity = (ConfigurableEntity) target;
            String configurableKey = ctx.getDefinition().getExt(KEY);
            if (null != configurableKey) {
                Field reflection = ctx.getDefinition().getField();
                if (reflection.getType().equals(List.class)) {
                    Class type = (Class) ((ParameterizedType) reflection.getGenericType()).getActualTypeArguments()[0];
                    return entity.getConfig().getList(configurableKey, type);
                }

                return entity.getConfig().get(configurableKey, reflection.getType());
            }
        }

        return null;
    }

    @Override
    public void interceptOnFormSaving(FieldSaveContext ctx) {
        String configurableKey = ctx.getDefinition().getExt(KEY);
        if (null != configurableKey) {
            ((ConfigurableEntity) ctx.getEntity()).addConfig(configurableKey, ctx.getFieldValue());
        }
    }
}
