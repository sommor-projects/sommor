package com.sommor.model;

import com.google.common.collect.Lists;
import com.sommor.core.context.Extensible;
import com.sommor.core.context.RequestContext;
import com.sommor.core.context.SubContext;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.model.define.FieldConfigDefineProcessor;
import com.sommor.model.define.FieldDefineInterceptor;
import com.sommor.model.define.FieldDefineProcessor;
import com.sommor.model.define.ModelAware;
import com.sommor.model.config.TargetAnnotation;
import com.sommor.model.config.TargetConfigDefinition;
import com.sommor.model.enricher.ModelEnrichContext;
import com.sommor.model.enricher.ModelEnrichProcessor;
import com.sommor.model.config.TargetConfig;
import com.sommor.model.fill.FieldFillContext;
import com.sommor.model.fill.FieldFillInterceptor;
import com.sommor.model.fill.FieldFillProcessor;
import com.sommor.model.fill.OnModelFill;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityFieldDefinition;
import com.sommor.mybatis.entity.definition.EntityManager;
import com.sommor.view.extension.ViewAnnotationRenderProcessor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public class ModelManager {
    private static final Map<Class, ModelDefinition> MODEL_MAP = new HashMap<>();

    public static ModelDefinition getModelDefinition(Class modelClass) {
        ModelDefinition modelDefinition = MODEL_MAP.get(modelClass);
        if (null == modelDefinition) {
            synchronized (MODEL_MAP) {
                modelDefinition = MODEL_MAP.get(modelClass);
                if (null == modelDefinition) {
                    modelDefinition = parseModelDefinition(modelClass);
                }
                MODEL_MAP.put(modelClass, modelDefinition);
            }
        }
        return modelDefinition;
    }

    private static ModelDefinition parseModelDefinition(Class modelClass) {
        ModelDefinition modelDefinition = new ModelDefinition();
        modelDefinition.setModelClass(modelClass);

        parseFieldDefinitions(modelDefinition, modelClass);
        parseModelEnrichers(modelDefinition, modelClass);

        return modelDefinition;
    }

    private static void parseFieldDefinitions(ModelDefinition modelDefinition, Class modelClass) {
        Class clazz = modelClass;

        while (null != clazz && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (! Modifier.isStatic(field.getModifiers())) {
                    Method getter = parseFieldGetterMethod(clazz, field);
                    Method setter = parseFieldSetterMethod(clazz, field);
                    if (null != getter && null != setter) {
                        FieldDefinition fieldDefinition = parseFieldDefinition(modelDefinition, clazz, field);
                        modelDefinition.add(fieldDefinition);
                    }
                }
            }

            clazz = clazz.getSuperclass();
        }
    }

    private static ExtensionExecutor<FieldDefineProcessor> fieldDefineProcessor = ExtensionExecutor.of(FieldDefineProcessor.class);
    private static ExtensionExecutor<FieldDefineInterceptor> fieldDefineInterceptor = ExtensionExecutor.of(FieldDefineInterceptor.class);
    private static ExtensionExecutor<FieldConfigDefineProcessor> fieldConfigDefineInterceptor = ExtensionExecutor.of(FieldConfigDefineProcessor.class);

    private static FieldDefinition parseFieldDefinition(ModelDefinition modelDefinition, Class clazz, Field field) {
        FieldDefinition definition = new FieldDefinition();

        field.setAccessible(true);
        definition.setField(field);
        definition.setFieldType(field.getType());
        definition.setDeclaringClass(clazz);

        definition.setName(field.getName());
        definition.setGetter(parseFieldGetterMethod(clazz, field));
        definition.setSetter(parseFieldSetterMethod(clazz, field));
        definition.setSubModelDefinition(parseSubModelDefinition(modelDefinition, field));

        TargetConfigDefinition fieldConfigDefinition = parseFieldConfigDefinition(field);
        definition.setFieldConfigDefinition(fieldConfigDefinition);

        if (null != fieldConfigDefinition) {
            Annotation annotation = fieldConfigDefinition.getAnnotation();
            fieldDefineProcessor.run(annotation,
                    ext -> ext.processOnFieldDefine(annotation, definition)
            );

            Class fieldConfigClass = fieldConfigDefinition.getTargetAnnotation().value();
            while (null != fieldConfigClass && fieldConfigClass != TargetConfig.class) {
                fieldConfigDefineInterceptor.run(fieldConfigClass, ext -> ext.processOnFieldConfigDefine(definition));
                fieldConfigClass = fieldConfigClass.getSuperclass();
            }
        }

        fieldDefineInterceptor.run(ext -> ext.interceptOnFieldDefine(definition));

        return definition;
    }

    private static void parseModelEnrichers(ModelDefinition modelDefinition, Class modelClass) {
        List<TargetConfigDefinition> modelEnricherAnnotations = parseTargetConfigDefinitions(modelClass.getAnnotations());
        modelDefinition.setModelEnricherConfigDefinitions(modelEnricherAnnotations);
    }

    private static ModelDefinition parseSubModelDefinition(ModelDefinition modelDefinition, Field field) {
        ModelDefinition subModelDefinition = null;

        ModelAware modelAware = field.getAnnotation(ModelAware.class);
        if (null == modelAware) {
            modelAware = field.getType().getAnnotation(ModelAware.class);
        }

        if (null != modelAware) {
            subModelDefinition = parseSubModelDefinition(modelAware, field);
            subModelDefinition.setParentModelDefinition(modelDefinition);
            subModelDefinition.setName(field.getName());
        }

        return subModelDefinition;
    }

    private static ModelDefinition parseSubModelDefinition(ModelAware modelAware, Field field) {
        Class modelClass = modelAware.value() == Void.class ? field.getType() : modelAware.value();
        ModelDefinition modelDefinition = parseModelDefinition(modelClass);
        return modelDefinition;
    }

    private static TargetConfigDefinition parseFieldConfigDefinition(Field field) {
        List<TargetConfigDefinition> annotationDefinitions = parseTargetConfigDefinitions(field.getAnnotations());
        if (CollectionUtils.isNotEmpty(annotationDefinitions)) {
            return annotationDefinitions.get(0);
        }
        return null;
    }

    private static Method parseFieldGetterMethod(Class clazz, Field field) {
        String getterName = parseFieldMethodName("get", field.getName());
        Method method = null;
        try {
            method = clazz.getMethod(getterName);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            // ignore
        }
        return method;
    }

    private static Method parseFieldSetterMethod(Class clazz, Field field) {
        String setterName = parseFieldMethodName("set", field.getName());
        Method setter = null;
        try {
            setter = clazz.getMethod(setterName, new Class[] {field.getType()});
            setter.setAccessible(true);
        } catch (NoSuchMethodException e) {
            // ignore
        }
        return setter;
    }

    private static String parseFieldMethodName(String methodPrefix, String fieldName) {
        return  methodPrefix + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
    }

    private static List<TargetConfigDefinition> parseTargetConfigDefinitions(Annotation[] annotations) {
        List<TargetConfigDefinition> results = new ArrayList<>();

        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationClass = annotation.annotationType();
            TargetAnnotation targetAnnotation = annotationClass.getAnnotation(TargetAnnotation.class);
            if (null != targetAnnotation) {
                results.add(new TargetConfigDefinition(targetAnnotation, annotation));
            } else {
                List<Annotation> repeatableAnnotations = parseRepeatableAnnotations(annotation);
                if (CollectionUtils.isNotEmpty(repeatableAnnotations)) {
                    List<TargetConfigDefinition> definitions = parseTargetConfigDefinitions(repeatableAnnotations.toArray(new Annotation[0]));
                    results.addAll(definitions);
                }
            }
        }

        return results;
    }

    private static List<Annotation> parseRepeatableAnnotations(Annotation annotation) {
        Class<? extends Annotation> annotationClass = annotation.annotationType();
        if (annotationClass.getDeclaredMethods().length == 1) {
            Method valueMethod;
            try {
                valueMethod = annotationClass.getDeclaredMethod("value");
            } catch (NoSuchMethodException e) {
                // ignore
                return Collections.emptyList();
            }

            try {
                Object o = valueMethod.invoke(annotation);
                if (o.getClass().isArray()) {
                    Object[] a = (Object[]) o;
                    if (a[0] instanceof Annotation) {
                        Annotation ann = (Annotation) a[0];
                        Repeatable repeatable = ann.annotationType().getAnnotation(Repeatable.class);
                        if (null != repeatable && repeatable.value() == annotationClass) {
                            List<Annotation> result = new ArrayList<>();
                            for (Object v : a) {
                                result.add((Annotation) v);
                            }
                            return result;
                        }
                    }
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        return Collections.emptyList();
    }

    public static Model parseModel(Object target) {
        return parseModel(target, null);
    }

    public static Model parseModel(Object target, Extensible extensible) {
        ModelDefinition modelDefinition = ModelManager.getModelDefinition(target.getClass());
        return ModelManager.parseModel(target, modelDefinition, extensible);
    }

    public static Model parseModel(Object target, ModelDefinition modelDefinition, Extensible extensible) {
        Model model = extensible == null ? new Model(target, modelDefinition) : new Model(target, modelDefinition, extensible);

        parseModelEnricherConfigs(model, modelDefinition);

        for (FieldDefinition fieldDefinition : modelDefinition.getFields()) {
            parseModelField(model, target, fieldDefinition);
        }

        enrichEntityFields(model, modelDefinition);

        return model;
    }

    private static void enrichEntityFields(Model model, ModelDefinition modelDefinition) {
        EntityDefinition entityDefinition = EntityManager.getDefinition(modelDefinition.getModelClass());

        if (entityDefinition != null) {
            String entityName = entityDefinition.getSubjectName();
            if (StringUtils.isNoneBlank(entityName)) {
                for (EntityFieldDefinition efd : entityDefinition.getFieldDefinitions()) {
                    String fieldName = efd.getFieldName();
                    String aliasName = entityName + (fieldName.substring(0,1).toUpperCase() + fieldName.substring(1));
                    model.addFieldAliasName(fieldName, aliasName);
                }
                model.addFieldAliasName("id", "entityId");
                DefaultModelField.add(model, "entityName", entityName);
            }
        }
    }

    private static void parseModelEnricherConfigs(Model model, ModelDefinition modelDefinition) {
        List<TargetConfigDefinition> modelEnricherConfigDefinitions = modelDefinition.getModelEnricherConfigDefinitions();
        if (CollectionUtils.isNotEmpty(modelEnricherConfigDefinitions)) {
            List<TargetConfig> modelEnricherConfigs = new ArrayList<>();
            for (TargetConfigDefinition targetConfigDefinition : modelEnricherConfigDefinitions) {
                TargetConfig modelEnricherConfig = parseTargetConfig(targetConfigDefinition, null);
                modelEnricherConfigs.add(modelEnricherConfig);
            }
            model.setModelEnricherConfigs(modelEnricherConfigs);
        }
    }

    public static void setModelFieldValues(Object target, Object source) {
        if (null == target || source == null) {
            return;
        }

        ModelDefinition targetModelDefinition = getModelDefinition(target.getClass());
        for (FieldDefinition sourceFieldDefinition : getModelDefinition(source.getClass()).getFields()) {
            FieldDefinition fieldDefinition = targetModelDefinition.getField(sourceFieldDefinition.getName());
            if (null != fieldDefinition) {
                Object sourceValue = sourceFieldDefinition.getFieldValue(source);
                sourceValue = convert(fieldDefinition.getFieldType(), sourceValue);
                if (checkFieldType(fieldDefinition.getFieldType(), sourceValue)) {
                    fieldDefinition.setFieldValue(target, sourceValue);
                }
            }
        }
    }

    public static void resetModelFieldValues(Object target) {
        if (null == target) {
            return;
        }

        ModelDefinition targetModelDefinition = getModelDefinition(target.getClass());
        for (FieldDefinition fieldDefinition : targetModelDefinition.getFields()) {
            fieldDefinition.setFieldValue(target, null);
        }
    }

    private static ExtensionExecutor<FieldFillProcessor> fieldFillProcessor = ExtensionExecutor.of(FieldFillProcessor.class);
    private static ExtensionExecutor<FieldFillInterceptor> fieldFillInterceptor = ExtensionExecutor.of(FieldFillInterceptor.class);

    public static void triggerOnModelFill(Model model, Model sourceModel) {
        Object target = model.getTarget();
        if (target instanceof OnModelFill) {
            ((OnModelFill) target).onModelFill(model, sourceModel);
        }
    }

    public static void fillModelFieldValues(Model model, Model sourceModel) {
        for (ModelField modelField : model.getFields()) {
            Object value = null;

            FieldFillContext ctx = new FieldFillContext(ModelManager.getModelFieldSubContext(modelField));
            ctx.setModel(model);
            ctx.setSourceModel(sourceModel);
            ctx.setModelField(modelField);

            TargetConfig fieldConfig = modelField.getFieldConfig();
            enrichTargetConfigFromSourceModel(fieldConfig, sourceModel);
            ctx.setFieldConfig(fieldConfig);

            if (null != fieldConfig) {
                value = fieldFillProcessor.executeFirstNotNull(fieldConfig, ext -> ext.processOnFieldFill(fieldConfig, ctx));
            }

            if (null == value) {
                value = fieldFillInterceptor.executeFirstNotNull(ext -> ext.interceptOnFieldFill(ctx));
            }

            if (null == value) {
                ModelField sourceModelField = sourceModel.getField(modelField.getName());
                if (null != sourceModelField) {
                    Object sourceValue = ModelManager.convert(modelField.getType(), sourceModelField.getValue());
                    if (checkFieldType(modelField, sourceValue)) {
                        value = sourceValue;
                    }
                }
            }

            if (null != value) {
                modelField.setValue(value);
            }

            Model subModel = modelField.getSubModel();
            if (null != subModel) {
                subModel.fill(sourceModel);
            }
        }
    }

    private static ExtensionExecutor<ModelEnrichProcessor> modelEnrichProcessor = ExtensionExecutor.of(ModelEnrichProcessor.class);

    public static void enrichModel(Model model, List<Model> sourceModels) {
        ModelEnrichContext ctx = new ModelEnrichContext(RequestContext.get());
        ctx.setModel(model);
        ctx.setSourceModels(sourceModels);

        List<TargetConfig> modelEnricherConfigs = model.getModelEnricherConfigs();
        if (CollectionUtils.isNotEmpty(modelEnricherConfigs)) {
            for (TargetConfig modelEnricherConfig : modelEnricherConfigs) {
                //enrichTargetConfigFromSourceModel(modelEnricherConfig, sourceModel);
                modelEnrichProcessor.run(modelEnricherConfig, ext -> ext.processOnModelEnrich(modelEnricherConfig, ctx));
            }
        }
    }

    public static void enrichModel(Model model, Model sourceModel) {
        enrichModel(model, Lists.newArrayList(sourceModel));
    }

    public static boolean checkFieldType(Class type, Object value) {
        return value != null && type.isAssignableFrom(value.getClass());
    }

    public static boolean checkFieldType(ModelField modelField, Object value) {
        return value != null && modelField.getType().isAssignableFrom(value.getClass());
    }

    private static void parseModelField(Model model, Object target, FieldDefinition fieldDefinition) {
        ReflectModelField modelField = new ReflectModelField(target, fieldDefinition);
        modelField.setModel(model);
        modelField.setName(fieldDefinition.getName());

        parseSubModel(model, modelField, fieldDefinition);
        parseFieldConfig(modelField, fieldDefinition);

        model.addField(modelField);
    }

    private static void parseFieldConfig(ReflectModelField modelField, FieldDefinition fieldDefinition) {
        TargetConfigDefinition fieldAnnotationDefinition = fieldDefinition.getFieldConfigDefinition();
        if (null != fieldAnnotationDefinition) {
            TargetConfig fieldConfig = parseTargetConfig(fieldAnnotationDefinition, fieldDefinition.getField());
            fieldConfig.annotation(fieldAnnotationDefinition.getAnnotation());
            modelField.setFieldConfig(fieldConfig);
        }
    }

    private static final ExtensionExecutor<ViewAnnotationRenderProcessor> viewAnnotationRenderProcessor = ExtensionExecutor.of(ViewAnnotationRenderProcessor.class);

    private static TargetConfig parseTargetConfig(TargetConfigDefinition targetConfigDefinition, Field field) {
        TargetAnnotation tca = targetConfigDefinition.getTargetAnnotation();
        Class targetConfigClass = tca.value();

        TargetConfig targetConfig;
        try {
            targetConfig = (TargetConfig) targetConfigClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException("new " + targetConfigClass.getName() + "() Exception", e);
        }

        targetConfig.field(field);
        Annotation fieldAnnotation = targetConfigDefinition.getAnnotation();
        initTargetConfig(targetConfig, fieldAnnotation);

        viewAnnotationRenderProcessor.run(fieldAnnotation, ext -> ext.processOnViewAnnotationRender(fieldAnnotation, targetConfig));

        return targetConfig;
    }

    private static void initTargetConfig(TargetConfig targetConfig, Annotation fieldAnnotation) {
        Model model = targetConfig.model();

        for (Method method : fieldAnnotation.annotationType().getDeclaredMethods()) {
            Object value;
            try {
                value = method.invoke(fieldAnnotation);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

            String fieldName = method.getName();

            if (fieldName.endsWith("FieldName")) {
                fieldName = fieldName.substring(0, fieldName.length()-9);

                String aliasFieldName = value.toString();
                if (StringUtils.isBlank(aliasFieldName)) {
                    Field field = targetConfig.field();
                    if (null != field) {
                        aliasFieldName = field.getName();
                        value = aliasFieldName;
                    }
                }

                if (StringUtils.isNotBlank(aliasFieldName) && model.hasField(fieldName)) {
                    targetConfig.fieldFillFromSourceModelMap().put(fieldName, aliasFieldName);
                }
            }

            model.setFieldValue(method.getName(), value);
        }
    }

    private static void enrichTargetConfigFromSourceModel(TargetConfig targetConfig, Model sourceModel) {
        if (null != targetConfig) {
            Model model = targetConfig.model();
            for (Map.Entry<String, String> entry : targetConfig.fieldFillFromSourceModelMap().entrySet()) {
                String fieldName = entry.getKey();
                String fieldAliasName = entry.getValue();
                if (sourceModel.hasField(fieldAliasName)) {
                    model.setFieldValue(fieldName, sourceModel.getFieldValue(fieldAliasName));
                }
            }
        }
    }

    private static void parseSubModel(Model model, ReflectModelField modelField, FieldDefinition fieldDefinition) {
        ModelDefinition subModelDefinition = fieldDefinition.getSubModelDefinition();
        if (null != subModelDefinition) {
            Object target = subModelDefinition.getModelClass() == fieldDefinition.getFieldType() ? modelField : null;
            Model subModel = parseModel(target, subModelDefinition, model);
            subModel.setName(modelField.getName());
            subModel.setParentModel(model);
            modelField.setSubModel(subModel);
        }
    }

    public static <T> T parseTarget(Object target) {
        if (target instanceof ModelField) {
            return (T) ((ModelField) target).getValue();
        }
        return (T) target;
    }

    public static Object convert(Class targetType, Object value) {
        if (null != value) {
            if (targetType == String.class && value.getClass() == Integer.class) {
                return String.valueOf(value);
            }
        }

        return value;
    }

    public static SubContext getModelFieldSubContext(ModelField modelField) {
        TargetConfig targetConfig = modelField.getFieldConfig();
        if (null!= targetConfig) {
            return RequestContext.getSubContext(targetConfig);
        }

        return RequestContext.getSubContext(modelField);
    }
}
