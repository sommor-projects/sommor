package com.sommor.view.model;

import com.sommor.view.field.FieldConfig;
import com.sommor.view.field.FieldView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/16
 */
public class ModelFieldConfig<V extends FieldView> extends FieldConfig<V> {

    @Setter
    private Object value;

    public <T> T getValue() {
        return (T) value;
    }

}
