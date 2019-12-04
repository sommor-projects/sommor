package com.sommor.view.form;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.mybatis.entity.definition.EntityClassParser;
import com.sommor.mybatis.entity.definition.EntityDefinitionFactory;
import com.sommor.view.config.FieldConfig;
import com.sommor.view.config.Fieldset;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/26
 */
public class FormManager {

    private static final Map<Class, FormDefinition> FORM_DEFINITION_MAP = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static FormDefinition getFormDefinition(Class formClass) {
        FormDefinition definition = FORM_DEFINITION_MAP.get(formClass);
        if (null == definition) {
            synchronized (FORM_DEFINITION_MAP) {
                definition = FORM_DEFINITION_MAP.get(formClass);
                if (null == definition) {
                    definition = new FormDefinition();
                    definition.setEntityDefinition(EntityDefinitionFactory.getDefinition(EntityClassParser.parse(formClass)));

                    FormFieldsetDefinition formFieldsetDefinition = new FormFieldsetDefinition();
                    parseFormFieldDefinition(formClass, formFieldsetDefinition);
                    definition.setFieldsetDefinition(formFieldsetDefinition);
                }

                FORM_DEFINITION_MAP.put(formClass, definition);
            }
        }

        return definition;
    }

    private static void parseFormFieldDefinition(Class formClass, FormFieldsetDefinition definition) {
        Class clazz = formClass;

        while (null != clazz && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                boolean parsed = parseAnnotations(field, field.getDeclaredAnnotations(), definition, formClass);
                if (! parsed) {
                    parseAnnotations(field, field.getType().getAnnotations(), definition, formClass);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private static boolean parseAnnotations(Field field, Annotation[] annotations, FormFieldsetDefinition definition, Class formClass) {
        boolean parsed = false;

        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationClass = annotation.annotationType();
            if (null != annotationClass) {
                FieldConfig fieldConfig = annotationClass.getAnnotation(FieldConfig.class);
                if (null != fieldConfig) {
                    if (annotationClass == Fieldset.class) {
                        parseFieldset(field, annotation, (Fieldset) annotation, definition, formClass);
                    } else {
                        FormFieldDefinition d = new FormFieldDefinition();
                        parseFieldFromFieldViewConfig(d, fieldConfig.value(), annotation, field, formClass);
                        d.setProcessFieldType(fieldConfig.processType());
                        definition.add(d);
                    }
                    parsed = true;
                }

                Fieldset fieldset = annotationClass.getAnnotation(Fieldset.class);
                if (null != fieldset) {
                    parseFieldset(field, annotation, fieldset, definition, formClass);
                    parsed = true;
                }
            }
        }

        return parsed;
    }

    private static void parseFieldset(Field field, Annotation annotation, Fieldset fieldset, FormFieldsetDefinition fieldsetDefinition, Class formClass) {
        FormFieldsetDefinition d = new FormFieldsetDefinition();

        FieldConfig fieldConfig = fieldset.annotationType().getAnnotation(FieldConfig.class);
        Class viewClass = fieldConfig.value();
        parseFieldFromFieldViewConfig(d, viewClass, annotation, field, formClass);

        Class subClass = fieldset.value();
        if (subClass != Void.class) {
            parseFormFieldDefinition(subClass, d);
        }

        d.setProcessFieldType(fieldset.processFieldType());

        fieldsetDefinition.add(d);
    }

    private static void parseFieldFromFieldViewConfig(FormFieldDefinition definition, Class viewClass, Annotation annotation, Field field, Class formClass) {
        definition.setField(field);
        definition.setConfig(annotation);

        String fieldName = field.getName();
        definition.setName(fieldName);
        definition.setViewClass(viewClass);

        String getterName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        String setterName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        Method getter;
        try {
            getter = formClass.getMethod(getterName);
            getter.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        Method setter;
        try {
            setter = formClass.getMethod(setterName, new Class[] {field.getType()});
            setter.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        definition.setSetter(setter);
        definition.setGetter(getter);

        enrichConstraints(field, definition);

        ExtensionExecutor.of(FieldConfigProcessor.class)
                .run(definition.getConfig(),
                        ext -> ext.processOnFieldInit(definition.getConfig(), definition)
                );
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
}
