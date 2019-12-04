package com.sommor.media.form;

import com.sommor.view.FieldView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/4
 */
public class PicturesFieldView extends FieldView {

    @Getter
    @Setter
    private Integer maxCount;

    public PicturesFieldView() {
        super("pictures");
    }

}
