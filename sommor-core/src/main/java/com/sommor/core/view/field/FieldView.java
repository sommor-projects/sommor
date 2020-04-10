package com.sommor.core.view.field;

import com.sommor.core.view.View;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/27
 */
public class FieldView extends View {

    @Getter
    @Setter
    private String title;

    public FieldView(String tag) {
        super(tag);
    }
}
