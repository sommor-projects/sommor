package com.sommor.view.model;

import com.sommor.view.field.FieldsetConfig;
import com.sommor.model.Model;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
public class ModelViewConfig<V extends ModelView> extends FieldsetConfig<V> {

    @Getter
    @Setter
    private Model model;
}
