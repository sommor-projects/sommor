package com.sommor.scaffold.view.field;

import com.sommor.scaffold.context.Extensible;
import lombok.Getter;
import lombok.Setter;

import javax.validation.groups.Default;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class FieldDefinition extends Extensible {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String style;

    @Getter
    @Setter
    private Set<String> features;

    @Getter
    @Setter
    private Class viewClass;

    @Getter
    @Setter
    private boolean processFieldType;

    @Getter
    @Setter
    private Field field;

    @Getter
    @Setter
    private Method getter;

    @Getter
    @Setter
    private Method setter;

    @Setter
    private Annotation fieldConfig;

    private Map<Class, FieldConstraints> constraintsMap = new HashMap<>();

    public FieldConstraints getConstraints(Class group) {
        FieldConstraints fieldConstraints = constraintsMap.computeIfAbsent(group, p->new FieldConstraints());
        return fieldConstraints;
    }

    public FieldConstraints getConstraints() {
        return getConstraints(Default.class);
    }

    public <Field extends Annotation> Field getFieldConfig() {
        return (Field) this.fieldConfig;
    }

    public void setFieldValue(Object target, Object value) {
        try {
            if (null != setter) {
                setter.invoke(target, value);
            } else {
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
            } else {
                return field.get(target);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasFeature(String feature) {
        return this.features == null ? false : this.features.contains(feature);
    }
}
