package com.sommor.scaffold.view.field;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class DataSource {

    private Object target;

    private FieldsetDefinition definition;

    private String[] fieldNames;

    public DataSource(Object target) {
        this.target = target;

        FieldsetDefinition definition = FieldManager.getFieldset(target.getClass());
        this.definition = definition;
        this.fieldNames = definition.getFields().stream()
                .map(p -> p.getName()).toArray(String[]::new);
    }

    public FieldDefinition getField(String fieldName) {
        return this.definition.getField(fieldName);
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public <T> T get(String fieldName) {
        FieldDefinition fd = this.definition.getField(fieldName);
        if (null != fd) {
            try {
                return (T) fd.getFieldValue(this.target);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void set(String fieldName, Object value) {
        FieldDefinition fd = this.definition.getField(fieldName);
        if (null != fd) {
            try {
                if (checkValueType(fd.getField().getType(), value)) {
                    fd.setFieldValue(this.target, value);
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> T getTarget() {
        return (T) this.target;
    }

    public Map<String, Object> toData() {
        return (Map<String, Object>) JSON.toJSON(this.target);
    }

    public <D> D to(Class<D> clazz) {
        D d;
        try {
            d = (D) clazz.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        DataSource ds = new DataSource(d);

        for (String fieldName : this.getFieldNames()) {
            FieldDefinition field = ds.getField(fieldName);
            if (null != field) {
                Object value = this.get(fieldName);
                ds.set(fieldName, value);
            }
        }

        return d;
    }

    protected boolean checkValueType(Class type, Object value) {
        return value != null && type.isAssignableFrom(value.getClass());
    }
}
