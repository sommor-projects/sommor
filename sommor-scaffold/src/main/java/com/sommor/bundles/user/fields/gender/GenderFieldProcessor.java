package com.sommor.bundles.user.fields.gender;

import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.view.field.FieldRenderContext;
import com.sommor.core.view.Option;
import com.sommor.core.view.SelectView;
import com.sommor.core.view.field.FieldProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Implement
public class GenderFieldProcessor implements FieldProcessor<GenderField> {

    @Override
    public void processOnFormRender(GenderField genderField, FieldRenderContext ctx) {
        SelectView selectView = ctx.getFieldView();

        selectView.addOption(new Option("男", UserEntity.GENDER_MALE));
        selectView.addOption(new Option("女", UserEntity.GENDER_FEMALE));
    }
}
