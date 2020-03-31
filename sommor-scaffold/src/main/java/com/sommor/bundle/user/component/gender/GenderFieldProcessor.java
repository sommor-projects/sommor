package com.sommor.bundle.user.component.gender;

import com.sommor.bundle.user.entity.UserEntity;
import com.sommor.component.form.field.SelectView;
import com.sommor.extensibility.config.Implement;
import com.sommor.component.form.field.Option;
import com.sommor.view.context.ViewRenderContext;
import com.sommor.view.extension.ViewRenderProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Implement
public class GenderFieldProcessor implements ViewRenderProcessor<GenderFieldConfig> {

    @Override
    public void processOnViewRender(GenderFieldConfig genderFieldConfig, ViewRenderContext ctx) {
        SelectView selectView = ctx.getView();
        selectView.addOption(new Option("男", UserEntity.GENDER_MALE));
        selectView.addOption(new Option("女", UserEntity.GENDER_FEMALE));
    }
}
