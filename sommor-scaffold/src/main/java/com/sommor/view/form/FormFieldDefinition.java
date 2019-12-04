package com.sommor.view.form;

import com.sommor.view.FieldConstraints;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Getter
@Setter
public class FormFieldDefinition {
    private String name;
    private Class viewClass;

    private boolean processFieldType;

    private Field field;
    private Method getter;
    private Method setter;
    private Annotation config;

    private FieldConstraints constraints = new FieldConstraints();
}
