package com.sommor.core.component.entity.name;

import com.sommor.core.component.form.field.SelectView;
import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityManager;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.extension.ViewRenderProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Implement
public class EntityNameSelectFieldProcessor implements ViewRenderProcessor<EntityNameSelectConfig> {

    @Override
    public void processOnViewRender(EntityNameSelectConfig entityNameSelectConfig, ViewRenderContext ctx) {
        SelectView selectView = ctx.getView();
        for (EntityDefinition ed : EntityManager.all()) {
            if (ed.getSubjectName() != null) {
                selectView.addOption(ed.getSubjectName(), ed.getSubjectName());
            }
        }
    }
}
