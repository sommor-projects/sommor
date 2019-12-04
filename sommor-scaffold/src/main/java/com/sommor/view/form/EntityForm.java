package com.sommor.view.form;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.FieldDefinition;
import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.scaffold.service.EntityAnnotatedProcessor;
import com.sommor.view.FieldView;
import com.sommor.view.FormView;

import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/1
 */
public class EntityForm<Entity> {

    public JSONObject toValues() {
        return (JSONObject) JSON.toJSON(this);
    }


    public void render(Entity entity, FormView formView, EntityFormRenderParam param) {
    }

    public void validate() {
    }

    public void save(Entity entity, Entity originalEntity) {
    }

    public void renderField(FieldView fieldView) {
    }

    @SuppressWarnings("unchecked")
    public Entity toEntity() {
        FormDefinition formDefinition = FormManager.getFormDefinition(this.getClass());

        EntityDefinition entityDefinition =  formDefinition.getEntityDefinition();
        Entity entity;
        try {
            entity = (Entity) entityDefinition.getEntityClass().newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        for (FieldDefinition fieldDefinition : entityDefinition.getFieldDefinitions()) {
            FormFieldDefinition formFieldDefinition = formDefinition.getField(fieldDefinition.getFieldName());
            if (null != formFieldDefinition) {
                Object value;
                try {
                    value = formFieldDefinition.getGetter().invoke(this);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }

                try {
                    fieldDefinition.getFieldSetMethod().invoke(entity, value);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return entity;
    }

    public void fromEntity(Entity entity) {
        FormDefinition formDefinition = FormManager.getFormDefinition(this.getClass());

        EntityDefinition entityDefinition =  formDefinition.getEntityDefinition();
        for (FieldDefinition fieldDefinition : entityDefinition.getFieldDefinitions()) {
            FormFieldDefinition formFieldDefinition = formDefinition.getField(fieldDefinition.getFieldName());
            if (null != formFieldDefinition) {
                Object value;
                try {
                    value = fieldDefinition.getFieldGetMethod().invoke(entity);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }

                try {
                    formFieldDefinition.getSetter().invoke(this, value);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
