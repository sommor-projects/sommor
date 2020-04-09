package com.sommor.model;

import com.sommor.core.context.Extensible;
import com.sommor.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public class ReflectModelField extends Extensible implements ModelField {

    private FieldDefinition fieldDefinition;

    @Getter
    @Setter
    private String name;

    private Object target;

    @Getter
    @Setter
    private Model model;

    @Getter
    @Setter
    private Model subModel;

    @Setter
    private TargetConfig fieldConfig;

    public ReflectModelField(Object target, FieldDefinition fieldDefinition) {
        super();

        this.target = target;
        this.fieldDefinition = fieldDefinition;
        this.extend(fieldDefinition);
    }

    @Override
    public void setValue(Object value) {
        Object target = ModelManager.parseTarget(this.target);
        if (null != target) {
            this.fieldDefinition.setFieldValue(target, value);
        }
    }

    @Override
    public Object getValue() {
        Object target = ModelManager.parseTarget(this.target);
        if (null != target) {
            return this.fieldDefinition.getFieldValue(target);
        }
        return null;
    }

    @Override
    public Class getType() {
        return fieldDefinition.getFieldType();
    }

    public Field getReflectField() {
        return fieldDefinition.getField();
    }

    @Override
    public <T extends TargetConfig> T getFieldConfig() {
        return (T) this.fieldConfig;
    }

    @Override
    public String toString() {
        return "ReflectModelField["+this.name+"]";
    }
}
