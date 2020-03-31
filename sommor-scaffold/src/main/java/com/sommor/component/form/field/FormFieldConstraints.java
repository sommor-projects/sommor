package com.sommor.component.form.field;

import com.sommor.component.form.Constraint;

import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/16
 */
public class FormFieldConstraints {

    private Map<Class, Constraint> constraintsMap = new HashMap<>();

    public Constraint getConstraint(Class group) {
        return constraintsMap.computeIfAbsent(group, p -> new Constraint());
    }


    public Constraint getConstraint() {
        return getConstraint(Default.class);
    }
}
