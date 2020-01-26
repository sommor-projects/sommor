package com.sommor.scaffold.view.field;

import com.sommor.scaffold.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/25
 */
public class EntityDetail implements OnFieldFill {

    @Getter
    @Setter
    @TextField(title = "ID")
    private Integer id;

    @Override
    public void onFieldFill(DataSource targetData) {
    }

    public void fill(Object source) {
        Fieldset.fillData(this, source);
    }
}
