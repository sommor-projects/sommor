package com.sommor.core.component.form.field;

import com.sommor.core.component.form.Constraint;
import com.sommor.core.view.model.ModelFieldConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
public class FormFieldConfig<FV extends FormFieldView> extends ModelFieldConfig<FV> {

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String pathName;

    @Getter
    @Setter
    private String style;

    @Getter
    @Setter
    private Constraint constraint;

    public Constraint constraint() {
        if (null == constraint) {
            constraint = new Constraint();
        }
        return constraint;
    }
}
