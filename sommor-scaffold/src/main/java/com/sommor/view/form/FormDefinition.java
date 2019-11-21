package com.sommor.view.form;

import com.sommor.mybatis.entity.definition.EntityDefinition;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@Getter
public class FormDefinition {

    private EntityDefinition entityDefinition;

    private List<FormFieldDefinition> fieldDefinitions;

    private Map<String, FormFieldDefinition> formFieldDefinitionMap;

    public FormDefinition(EntityDefinition entityDefinition, List<FormFieldDefinition> fieldDefinitions) {
        this.entityDefinition = entityDefinition;
        this.fieldDefinitions = fieldDefinitions;
        this.formFieldDefinitionMap = fieldDefinitions.stream().collect(Collectors.toMap(FormFieldDefinition::getName, p -> p));
    }

    public FormFieldDefinition getField(String fieldName) {
        return this.formFieldDefinitionMap.get(fieldName);
    }
}
