package com.sommor.core.model;

import com.sommor.core.context.Extensible;
import com.sommor.core.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public class DefaultModelField extends Extensible implements ModelField {

    @Getter
    @Setter
    private Class type;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Object value;

    @Getter
    @Setter
    private Model model;

    @Getter
    @Setter
    private Model subModel;

    @Getter
    @Setter
    private TargetConfig fieldConfig;

    public DefaultModelField() {
    }

    public DefaultModelField(Extensible extensible) {
        super(extensible);
    }

    public static ModelField add(Model model, String fieldName, Object fieldValue) {
        DefaultModelField modelField = new DefaultModelField();
        modelField.setName(fieldName);
        modelField.setType(fieldValue.getClass());
        modelField.setValue(fieldValue);
        modelField.setModel(model);

        model.addField(modelField);

        return modelField;
    }
}
