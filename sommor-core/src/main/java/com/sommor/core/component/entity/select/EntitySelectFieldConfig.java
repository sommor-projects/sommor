package com.sommor.core.component.entity.select;

import com.sommor.core.component.form.field.FormFieldConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/29
 */
public class EntitySelectFieldConfig extends FormFieldConfig<EntitySelectView> {

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String entityName;

    @Getter
    @Setter
    private Class paramClass;

    @Getter
    private Map<String, Object> entityConditions;

    public void addCondition(String key, Object value) {
        if (null == entityConditions) {
            entityConditions = new HashMap<>();
        }
        entityConditions.put(key, value);
    }
}
