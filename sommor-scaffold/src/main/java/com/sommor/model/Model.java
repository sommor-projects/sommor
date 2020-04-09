package com.sommor.model;

import com.alibaba.fastjson.JSON;
import com.sommor.core.context.Extensible;
import com.sommor.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public class Model extends Extensible {

    private Object target;

    private ModelDefinition modelDefinition;

    @Getter
    @Setter
    private String name;

    @Getter
    private List<String> fieldAliasNames = new ArrayList<>();

    private Map<String, ModelField> fieldMap = new HashMap<>();

    private List<ModelField> fields = new ArrayList<>();

    @Getter
    @Setter
    private List<TargetConfig> modelEnricherConfigs;

    @Getter
    @Setter
    private Model parentModel;

    public Model() {
    }

    public Model(Object target, ModelDefinition modelDefinition) {
        this.target = target;
        this.modelDefinition = modelDefinition;
    }

    public Model(Object target, ModelDefinition modelDefinition, Extensible extensible) {
        super(extensible);
        this.target = target;
        this.modelDefinition = modelDefinition;
    }

    public Model(Object target, Extensible extensible) {
        super(extensible);
        this.target = target;
    }

    public String getPathName() {
        Model parentModel = this.getParentModel();
        if (null == parentModel) {
            return getName();
        }

        List<String> paths = new ArrayList<>();
        Model model = this;
        while (null != model) {
            if (StringUtils.isNotBlank(model.getName())) {
                paths.add(model.getName());
            } else {
                break;
            }
            model = model.getParentModel();
        }

        StringBuilder builder = new StringBuilder();
        for (int i=paths.size()-1; i>=0; i--) {
            if (builder.length() > 0) {
                builder.append(".");
            }
            builder.append(paths.get(i));
        }
        return builder.toString();
    }

    public <T> T getTarget() {
        return ModelManager.parseTarget(this.target);
    }

    public ModelField getField(String fieldName) {
        return this.fieldMap.get(fieldName);
    }

    public <V> V getFieldValue(String fieldName) {
        ModelField modelField = this.fieldMap.get(fieldName);
        if (null != modelField) {
            return (V) modelField.getValue();
        }
        return null;
    }

    public boolean hasField(String fieldName) {
        return this.fieldMap.containsKey(fieldName);
    }

    public List<ModelField> getFields() {
        return this.fields;
    }

    public void addField(ModelField modelField) {
        if (! this.fieldMap.containsKey(modelField.getName())) {
            this.fieldMap.put(modelField.getName(), modelField);
            this.fields.add(modelField);
        }
    }

    public void setFieldValue(String fieldName, Object value) {
        ModelField modelField = this.getField(fieldName);
        if (null != modelField) {
            value = ModelManager.convert(modelField.getType(), value);
            if (ModelManager.checkFieldType(modelField, value)) {
                modelField.setValue(value);
            }
        }
    }

    public void addFieldAliasName(String fieldName, String aliasName) {
        if (! this.fieldMap.containsKey(aliasName) && this.fieldMap.containsKey(fieldName)) {
            this.fieldMap.put(aliasName, this.fieldMap.get(fieldName));
            this.fieldAliasNames.add(aliasName);
        }
    }

    public void extend(Model extendModel, String... fieldNames) {
        Object target = extendModel.getTarget();
        if (null != target) {
            this.addExt(target.getClass(), target);
        }

        Map<String, ModelField> modelFieldMap;
        List<ModelField> modelFields;

        if (fieldNames.length > 0) {
            modelFieldMap = new HashMap<>();
            modelFields = new ArrayList<>();
            for (String fieldName : fieldNames) {
                ModelField modelField = extendModel.getField(fieldName);
                modelFieldMap.put(fieldName, modelField);
                modelFields.add(modelField);
            }
        } else {
            modelFieldMap = extendModel.fieldMap;
            modelFields = extendModel.fields;
        }

        for (Map.Entry<String, ModelField> entry : modelFieldMap.entrySet()) {
            String fieldName = entry.getKey();
            ModelField modelField = entry.getValue();
            if (! this.fieldMap.containsKey(fieldName)) {
                this.fieldMap.put(fieldName, modelField);
            }
        }

        this.fields.addAll(modelFields);
    }

    public void set(Model sourceModel) {
        ModelManager.setModelFieldValues(this.getTarget(), sourceModel.getTarget());
    }

    public void reset() {
        ModelManager.resetModelFieldValues(this.getTarget());
    }

    public Model enrich(Model sourceModel) {
        ModelManager.enrichModel(this, sourceModel);
        return this;
    }

    public Model enrich(List<Model> sourceModels) {
        ModelManager.enrichModel(this, sourceModels);
        return this;
    }

    public Model fill() {
        return this.fill(new Model());
    }

    public Model fill(Model sourceModel) {
        ModelManager.triggerOnModelFill(this, sourceModel);
        ModelManager.fillModelFieldValues(this, sourceModel);
        return this;
    }

    public Map<String, Object> toMap() {
        Object target = this.getTarget();
        if (null == target) {
            return Collections.emptyMap();
        }

        return (Map<String, Object>) JSON.toJSON(target);
    }

    public <Target> Target to(Class<Target> clazz) {
        Target target;
        try {
            target = (Target) clazz.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        ModelManager.setModelFieldValues(target, this.getTarget());
        return target;
    }

    public static Model of(Object target) {
        return ModelManager.parseModel(target);
    }

    public static Model of(Object target, Extensible extensible) {
        return ModelManager.parseModel(target, extensible);
    }

    public static void fillData(Object target, Object source) {
        fillData(Model.of(target), Model.of(source));
    }

    public static void fillData(Object target, Model sourceModel) {
        fillData(Model.of(target), sourceModel);
    }

    public static void fillData(Model targetModel, Model sourceModel) {
        targetModel.fill(sourceModel);
    }
}
