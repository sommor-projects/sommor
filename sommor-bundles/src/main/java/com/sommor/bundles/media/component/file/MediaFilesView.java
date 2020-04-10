package com.sommor.bundles.media.component.file;

import com.sommor.core.component.form.field.FormFieldView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/4
 */
public class MediaFilesView extends FormFieldView {

    @Getter
    @Setter
    private Integer maxCount;

    public MediaFilesView() {
        super("files");
    }

}
