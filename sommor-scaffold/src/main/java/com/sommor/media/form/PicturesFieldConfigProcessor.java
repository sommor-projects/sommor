package com.sommor.media.form;

import com.sommor.extensibility.config.Implement;
import com.sommor.view.FieldView;
import com.sommor.view.form.FieldConfigProcessor;
import com.sommor.view.form.EntityForm;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/4
 */
@Implement
public class PicturesFieldConfigProcessor implements FieldConfigProcessor<PicturesFieldConfig> {

    @Override
    public void processOnFormRender(PicturesFieldConfig picturesFieldConfig, FieldView view, EntityForm form) {
        PicturesFieldView picturesFieldView = (PicturesFieldView) view;
        picturesFieldView.setMaxCount(picturesFieldConfig.maxCount());
    }
}
