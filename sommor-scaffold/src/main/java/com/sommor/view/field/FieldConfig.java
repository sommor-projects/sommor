package com.sommor.view.field;

import com.sommor.view.ViewConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/27
 */
public class FieldConfig<FV extends FieldView> extends ViewConfig<FV> {

    @Getter
    @Setter
    private String title;

}
