package com.sommor.core.view.field;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
public class FieldsetDefinition extends FieldDefinition {

    @Getter
    private List<FieldDefinition> fields = new ArrayList<>();

    private Map<String, FieldDefinition> fieldMap = new HashMap<>();

    public void add(FieldDefinition definition) {
        this.fields.add(definition);
        this.fieldMap.put(definition.getName(), definition);
    }

    public FieldDefinition getField(String fieldName) {
        return this.fieldMap.get(fieldName);
    }
}
