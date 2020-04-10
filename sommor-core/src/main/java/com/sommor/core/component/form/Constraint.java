package com.sommor.core.component.form;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class Constraint {

    private static final String REQUIRED = "required";

    private static final String MIN_LENGTH = "minLength";

    private static final String MAX_LENGTH = "maxLength";

    private static final String DISABLED = "disabled";

    private Map<String, Object> constraints = new HashMap<>();

    public Constraint() {
    }

    public Constraint(Constraint constraint) {
        this.merge(constraint);
    }

    public Constraint merge(Constraint constraints) {
        this.constraints.putAll(constraints.constraints);
        return this;
    }

    public Constraint required() {
        this.constraints.put(REQUIRED, true);
        return this;
    }

    public Boolean isRequired() {
        return this.checkBoolean(REQUIRED);
    }

    public Constraint disabled() {
        this.constraints.put(DISABLED, true);
        return this;
    }

    public Boolean isDisabled() {
        return this.checkBoolean(DISABLED);
    }


    public Constraint minLength(int minLength) {
        this.constraints.put(MIN_LENGTH, minLength);
        return this;
    }

    public Integer getMinLength() {
        return (Integer) this.constraints.get(MIN_LENGTH);
    }

    public Constraint maxLength(int maxLength) {
        this.constraints.put(MAX_LENGTH, maxLength);
        return this;
    }

    public Integer getMaxLength() {
        return (Integer) this.constraints.get(MAX_LENGTH);
    }

    private Boolean checkBoolean(String key) {
        Boolean bool = (Boolean) this.constraints.get(key);
        if (null != bool) {
            return Boolean.TRUE.equals(bool);
        }
        return null;
    }
}
