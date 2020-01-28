package com.sommor.scaffold.view.field;

import com.sommor.scaffold.view.FieldView;
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
