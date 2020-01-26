package com.sommor.scaffold.view.field;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.scaffold.view.field.config.FieldConfig;
import com.sommor.scaffold.view.field.config.FieldsetConfig;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/26
 */
public class FieldManager {

    private static final Map<Class, FieldsetDefinition> FIELDSET_MAP = new HashMap<>();

    public static FieldsetDefinition getFieldset(Class targetClass) {
        FieldsetDefinition fieldset = FIELDSET_MAP.get(targetClass);
        if (null == fieldset) {
            synchronized (FIELDSET_MAP) {
                fieldset = FIELDSET_MAP.get(targetClass);
                if (null == fieldset) {
                    fieldset = new FieldsetDefinition();
                    parseFormFieldDefinition(targetClass, fieldset);
                }

                FIELDSET_MAP.put(targetClass, fieldset);
            }
        }

        return fieldset;
    }

    private static void parseFormFieldDefinition(Class targetClass, FieldsetDefinition fieldset) {
        Class clazz = targetClass;

        while (null != clazz && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                Method getter = getGetterMethod(clazz, field);
                Method setter = getSetterMethod(clazz, field);

                FieldDefinition definition = parseFieldDefinition(field);

                definition.setName(field.getName());
                definition.setGetter(getter);
                definition.setSetter(setter);

                enrichConstraints(field, definition);
                processOnInit(definition);

                fieldset.add(definition);
            }

            clazz = clazz.getSuperclass();
        }
    }

    private static FieldDefinition parseFieldDefinition(Field field) {
        FieldDefinition definition;

        Annotation fieldConfig = parseFieldConfig(field.getDeclaredAnnotations());
        if (null != fieldConfig) {
            definition = parseFieldDefinition(fieldConfig);
        } else {
            fieldConfig = parseFieldConfig(field.getType().getAnnotations());
            if (null != fieldConfig) {
                definition = parseFieldDefinition(fieldConfig);
            } else {
                definition = new FieldDefinition();
            }
        }

        definition.setField(field);

        return definition;
    }

    private static FieldDefinition parseFieldDefinition(Annotation fieldConfig) {
        FieldDefinition definition;
        if (fieldConfig.annotationType() == FieldsetConfig.class) {
            definition = new FieldsetDefinition();
            Class subClass = ((FieldsetConfig) fieldConfig).value();
            parseFormFieldDefinition(subClass, (FieldsetDefinition) definition);
        } else {
            definition = new FieldDefinition();
        }

        enrichFromFieldConfig(definition, fieldConfig);

        return definition;
    }

    private static Annotation parseFieldConfig(Annotation[] annotations) {
        boolean parsed = false;

        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationClass = annotation.annotationType();
            if (null != annotationClass) {
                FieldConfig fieldConfig = annotationClass.getAnnotation(FieldConfig.class);
                if (null != fieldConfig) {
                    return annotation;
                }
            }
        }

        return null;
    }

    private static void enrichFromFieldConfig(FieldDefinition definition, Annotation annotation) {
        definition.setFieldConfig(annotation);

        FieldConfig config = annotation.annotationType().getAnnotation(FieldConfig.class);
        definition.setViewClass(config.value());

        Map<String, Method> configMethods = Arrays.asList(annotation.annotationType().getDeclaredMethods())
                .stream()
                .collect(Collectors.toMap(p->p.getName(), p -> p));

        Method titleMethod = configMethods.get("title");
        if (null != titleMethod) {
            try {
                String title = (String) titleMethod.invoke(annotation);
                definition.setTitle(title);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        Method styleMethod = configMethods.get("style");
        if (null != styleMethod) {
            try {
                String style = (String) styleMethod.invoke(annotation);
                definition.setStyle(style);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        Method disabledMethod = configMethods.get("disabled");
        if (null != disabledMethod) {
            try {
                Boolean disabled = (Boolean) disabledMethod.invoke(annotation);
                if (Boolean.TRUE.equals(disabled)) {
                    definition.getConstraints().disabled();
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void processOnInit(FieldDefinition fd) {
        Annotation fieldConfig = fd.getFieldConfig();
        if (null != fieldConfig) {
            ExtensionExecutor.of(FieldProcessor.class)
                    .run(fieldConfig,
                            ext -> ext.processOnInit(fieldConfig, fd)
                    );
            ExtensionExecutor.of(FieldInterceptor.class)
                    .run(ext -> ext.interceptOnInit(fd));
        }
    }

    private static Method getSetterMethod(Class clazz, Field field) {
        String fieldName = field.getName();
        String setterName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        Method setter = null;
        try {
            setter = clazz.getMethod(setterName, new Class[] {field.getType()});
            setter.setAccessible(true);
        } catch (NoSuchMethodException e) {
            // ignore
        }
        return setter;
    }

    private static Method getGetterMethod(Class clazz, Field field) {
        String fieldName = field.getName();
        String getterName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        Method method = null;
        try {
            method = clazz.getMethod(getterName, new Class[] {field.getType()});
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            // ignore
        }
        return method;
    }

    private static void enrichConstraints(Field field, FieldDefinition definition) {
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
