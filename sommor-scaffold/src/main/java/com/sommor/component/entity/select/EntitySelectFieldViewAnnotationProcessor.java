package com.sommor.component.entity.select;

import com.sommor.extensibility.config.Implement;
import com.sommor.view.extension.ViewAnnotationRenderProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/16
 */
@Implement
public class EntitySelectFieldViewAnnotationProcessor implements ViewAnnotationRenderProcessor<EntitySelectField, EntitySelectFieldConfig> {

    @Override
    public void processOnViewAnnotationRender(EntitySelectField entitySelectField, EntitySelectFieldConfig config) {
        for (String condition : entitySelectField.entityConditions()) {
            String[] s = condition.split("=");
            config.addCondition(s[0].trim(), s[1].trim());
        }
    }
}
