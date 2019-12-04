package com.sommor.view.form;

import com.sommor.mybatis.entity.definition.EntityDefinition;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/26
 */
public class FormDefinition {

    @Getter
    @Setter
    private EntityDefinition entityDefinition;

    @Getter
    @Setter
    private FormFieldsetDefinition fieldsetDefinition;

    public FormFieldDefinition getField(String fieldName) {
        return null == fieldsetDefinition ? null : fieldsetDefinition.getField(fieldName);
    }

    public List<FormFieldDefinition> getFields() {
        return null == fieldsetDefinition ? Collections.emptyList() : fieldsetDefinition.getFields();
    }

}
