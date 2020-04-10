package com.sommor.bundles.user.component.gender;

import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.core.component.form.field.SelectView;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.component.form.field.Option;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.extension.ViewRenderProcessor;

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
