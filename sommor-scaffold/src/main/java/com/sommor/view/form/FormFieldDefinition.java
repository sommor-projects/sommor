package com.sommor.view.form;

import com.sommor.view.FieldConstraints;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Getter
public class FormFieldDefinition {

    private String name;
    private Class viewClass;

    private Field field;
    private Method method;
    private Annotation config;

    private FieldConstraints constraints = new FieldConstraints();

    public FormFieldDefinition(String name, Annotation config, Field field, Method method, Class viewClass) {
        this.name = name;
        this.viewClass = viewClass;
        this.config = config;
        this.field = field;
        this.method = method;
    }
}
