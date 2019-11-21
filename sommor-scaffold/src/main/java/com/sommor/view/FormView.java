package com.sommor.view;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.mybatis.entity.definition.EntityClassParser;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityDefinitionFactory;
import com.sommor.mybatis.entity.definition.FieldDefinition;
import com.sommor.view.form.*;
import com.sommor.view.html.HtmlElement;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class FormView<Entity> extends View {

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

    public Map<String, FieldView> getFieldViews() {
        Map<String, FieldView> fieldViewMap = new HashMap<>();

        for (FormField formField : this.formFields.values()) {
            FieldView fieldView = formField.newFieldView();
            fieldViewMap.put(fieldView.getName(), fieldView);
        }

        return fieldViewMap;
    }

    private void initAnnotatedFormField() {
        FormDefinition formDefinition = getFormDefinition(this.getClass());
        FormView formView = this;

        for (FormFieldDefinition formFieldDefinition : formDefinition.getFieldDefinitions()) {
            FormField formField = new AnnotatedFormField(formFieldDefinition, formView);
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

    private static final Map<Class, FormDefinition> FORM_DEFINITION_MAP = new HashMap<>();

    @SuppressWarnings("unchecked")
    private static FormDefinition getFormDefinition(Class formClass) {
        FormDefinition formDefinition = FORM_DEFINITION_MAP.get(formClass);
        if (null == formDefinition) {
            synchronized (FORM_DEFINITION_MAP) {
                formDefinition = FORM_DEFINITION_MAP.get(formClass);
                if (null == formDefinition) {
                    List<FormFieldDefinition> fieldDefinitions = new ArrayList<>();

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

                                        enrichConstraints(field, definition);

                                        ExtensionExecutor.of(FormFieldResolver.class)
                                            .run(definition.getConfig(),
                                                ext -> ext.resolveOnInit(definition.getConfig(), definition)
                                            );

                                        fieldDefinitions.add(definition);
                                    }
                                }
                            }
                        }
                        clazz = clazz.getSuperclass();
                    }

                    Class entityClass = EntityClassParser.parse(formClass);
                    EntityDefinition entityDefinition = EntityDefinitionFactory.getDefinition(entityClass);
                    formDefinition = new FormDefinition(entityDefinition, fieldDefinitions);
                }

                FORM_DEFINITION_MAP.put(formClass, formDefinition);
            }
        }

        return formDefinition;
    }

    private static void enrichConstraints(Field field, FormFieldDefinition definition) {

        NotBlank notBlank = field.getAnnotation(NotBlank.class);
        if (null != notBlank) {
            definition.getConstraints().required();
        }

        NotNull notNull = field.getAnnotation(NotNull.class);
        if (null != notNull) {
            definition.getConstraints().required();
        }

        Size size = field.getAnnotation(Size.class);
        if (null != size) {
            if (size.min() > 0) {
                definition.getConstraints().minLength(size.min());
            }
            if (size.max() > 0) {
                definition.getConstraints().maxLength(size.max());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Entity toEntity() {
        FormDefinition formDefinition = getFormDefinition(this.getClass());

        EntityDefinition entityDefinition =  formDefinition.getEntityDefinition();
        Entity entity;
        try {
            entity = (Entity) entityDefinition.getEntityClass().newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        for (FieldDefinition fieldDefinition : entityDefinition.getFieldDefinitions()) {
            FormFieldDefinition formFieldDefinition = formDefinition.getField(fieldDefinition.getFieldName());
            if (null != formFieldDefinition) {
                Object value;
                try {
                    value = formFieldDefinition.getMethod().invoke(this);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }

                try {
                    fieldDefinition.getFieldSetMethod().invoke(entity, value);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return entity;
    }
}
