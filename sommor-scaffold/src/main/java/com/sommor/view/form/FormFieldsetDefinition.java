package com.sommor.view.form;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
public class FormFieldsetDefinition extends FormFieldDefinition {

    @Getter
    private List<FormFieldDefinition> fields = new ArrayList<>();

    private Map<String, FormFieldDefinition> fieldMap = new HashMap<>();

    public void add(FormFieldDefinition definition) {
        this.fields.add(definition);
        this.fieldMap.put(definition.getName(), definition);
    }

    public FormFieldDefinition getField(String fieldName) {
        return this.fieldMap.get(fieldName);
    }
}
