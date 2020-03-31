package com.sommor.model;

import com.sommor.model.config.TargetConfigDefinition;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public class ModelDefinition {


    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Class modelClass;

    @Getter
    private List<FieldDefinition> fields = new ArrayList<>();

    private Map<String, FieldDefinition> fieldMap = new HashMap<>();

    @Getter
    @Setter
    private ModelDefinition parentModelDefinition;

    @Getter
    @Setter
    private List<TargetConfigDefinition> ModelEnricherConfigDefinitions;

    public void add(FieldDefinition definition) {
        definition.setModelDefinition(this);
        if (! this.fieldMap.containsKey(definition.getName())) {
            this.fields.add(definition);
            this.fieldMap.put(definition.getName(), definition);
        }
    }

    public FieldDefinition getField(String fieldName) {
        return this.fieldMap.get(fieldName);
    }

    @Override
    public String toString() {
        return "ModelDefinition["+this.modelClass.getName()+"]";
    }

}
