package com.sommor.view;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.view.form.AnnotatedFormField;
import com.sommor.view.form.FormFieldDefinition;
import com.sommor.view.form.FormField;
import com.sommor.view.form.FormFieldResolver;
import com.sommor.view.html.HtmlElement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class FormView extends View {

    private Map<String, FormField> formFields = new HashMap<>();

    public FormView() {
        super("form");
        this.setName(this.getClass().getSimpleName());

        this.initAnnotatedFormField();
    }

    public void addField(FormField formField) {
        this.formFields.put(formField.getName(), formField);
    }

    public FormField getFormField(String name) {
        return this.formFields.get(name);
    }

    private void initAnnotatedFormField() {
        List<FormFieldDefinition> annotations = getFormFieldDefinition(this.getClass());
        FormView formView = this;

        List<FormField> formFields = new ArrayList<>(annotations.size());

        for (FormFieldDefinition annotation : annotations) {
            FormField formField = new AnnotatedFormField(annotation, formView);
            this.addField(formField);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public HtmlElement toHtml() {
        List<FieldView> fieldViews = new ArrayList<>();

        for (FormField formField : this.formFields.values()) {
            FieldView fieldView = formField.newFieldView();
            fieldViews.add(fieldView);
        }

        return super.toHtml()
            .value(fieldViews);
    }

    private static final Map<Class, List<FormFieldDefinition>> FORM_FIELD_DEFINITION_MAP = new HashMap<>();

    @SuppressWarnings("unchecked")
    private static List<FormFieldDefinition> getFormFieldDefinition(Class formClass) {
        List<FormFieldDefinition> definitions = FORM_FIELD_DEFINITION_MAP.get(formClass);
        if (null == definitions) {
            synchronized (FORM_FIELD_DEFINITION_MAP) {
                definitions = FORM_FIELD_DEFINITION_MAP.get(formClass);
                if (null == definitions) {
                    definitions = new ArrayList<>();

                    Class clazz = formClass;
                    while (null != clazz && clazz != Object.class) {
                        for (Field field : clazz.getDeclaredFields()) {
                            for (Annotation annotation : field.getDeclaredAnnotations()) {
                                Class<? extends Annotation> annotationClass = annotation.annotationType();
                                if (null != annotationClass) {
                                    com.sommor.view.config.FieldView fieldView = annotationClass.getAnnotation(
                                        com.sommor.view.config.FieldView.class);
                                    if (null != fieldView) {
                                        String fieldName = field.getName();
                                        Class viewClass = fieldView.value();
                                        String methodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                                        Method method;
                                        try {
                                            method = formClass.getMethod(methodName);
                                            method.setAccessible(true);
                                        } catch (NoSuchMethodException e) {
                                            throw new RuntimeException(e);
                                        }

                                        FormFieldDefinition definition = new FormFieldDefinition(fieldName, annotation, field, method, viewClass);
                                        ExtensionExecutor.of(FormFieldResolver.class)
                                            .run(definition.getConfig(),
                                                ext -> ext.resolveOnInit(definition.getConfig(), definition)
                                            );

                                        definitions.add(definition);
                                    }
                                }
                            }
                        }
                        clazz = clazz.getSuperclass();
                    }
                }

                FORM_FIELD_DEFINITION_MAP.put(formClass, definitions);
            }
        }

        return definitions;
    }
}
