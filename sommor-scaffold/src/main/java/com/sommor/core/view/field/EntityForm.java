package com.sommor.core.view.field;

import com.sommor.core.view.FieldView;
import com.sommor.core.view.FormFieldView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/29
 */
public class EntityForm implements OnFieldRender, OnFill {
    @Getter
    @Setter
    private Integer id;

    @Override
    public void onFieldRender(FieldView fieldView) {
    }

    @Override
    public void onFill(DataSource targetData) {
    }
}
