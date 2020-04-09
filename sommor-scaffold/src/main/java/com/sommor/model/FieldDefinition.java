package com.sommor.model;

import com.sommor.core.context.Extensible;
import com.sommor.model.config.TargetConfigDefinition;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public class FieldDefinition extends Extensible {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Field field;

    @Getter
    @Setter
    private Class fieldType;

    @Getter
    @Setter
    private Class declaringClass;

    @Getter
    @Setter
    private Method getter;

    @Getter
    @Setter
    private Method setter;

    @Getter
    @Setter
    private ModelDefinition modelDefinition;

    @Getter
    @Setter
    private ModelDefinition subModelDefinition;

    @Getter
    @Setter
    private TargetConfigDefinition fieldConfigDefinition;

    public String getPathName() {
        if (null == this.getModelDefinition().getName()) {
            return this.getName();
        }

        List<String> namePaths = new ArrayList<>();
        namePaths.add(this.getName());

        ModelDefinition modelDefinition = this.getModelDefinition();
        while (null != modelDefinition) {
            String modelName = modelDefinition.getName();
            if (null == modelName) {
                break;
            }

            namePaths.add(modelName);
            modelDefinition = modelDefinition.getParentModelDefinition();
        }

        Collections.reverse(namePaths);
        return namePaths.stream().collect(Collectors.joining("."));
    }

    public void setFieldValue(Object target, Object value) {
        try {
            if (null != setter) {
                setter.invoke(target, value);
            } else if (null != field) {
                field.set(target, value);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Object getFieldValue(Object target) {
        try {
            if (null != getter) {
                return getter.invoke(target);
            } else if (null != field) {
                return field.get(target);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        if (null != field) {
            return field.getAnnotation(annotationClass);
        }

        return null;
    }

    @Override
    public String toString() {
        return "FieldDefinition[name="+this.getName()+"]";
    }
}
