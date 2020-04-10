package com.sommor.core.component.entity.select;

import com.sommor.core.component.form.field.SelectView;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/26
 */
public class EntitySelectView extends SelectView {

    @Getter
    @Setter
    private String entityName;

    @Getter
    @Setter
    private Map<String, Object> entityConditions;

    public EntitySelectView() {
        super("subject-select");
    }
}
