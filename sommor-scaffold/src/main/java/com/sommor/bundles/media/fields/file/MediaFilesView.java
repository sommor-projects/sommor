package com.sommor.bundles.media.fields.file;

import com.sommor.core.view.FormFieldView;
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
