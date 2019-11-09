package com.sommor.view;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class FieldConstraints {

    private static final String REQUIRED = "required";

    private static final String MAX_LENGTH = "maxLength";

    private Map<String, Object> constraints = new HashMap<>();

    public FieldConstraints() {
    }

    public FieldConstraints(FieldConstraints fieldConstraints) {
        this.merge(fieldConstraints);
    }

    public FieldConstraints merge(FieldConstraints constraints) {
        this.constraints.putAll(constraints.constraints);
        return this;
    }

    public FieldConstraints required() {
        this.constraints.put(REQUIRED, true);
        return this;
    }

    public boolean isRequired() {
        return Boolean.TRUE.equals(this.constraints.get(REQUIRED));
    }

    public FieldConstraints maxLength(int maxLength) {
        this.constraints.put(MAX_LENGTH, maxLength);
        return this;
    }

    public Integer getMaxLength() {
        return (Integer) this.constraints.get(MAX_LENGTH);
    }
}
