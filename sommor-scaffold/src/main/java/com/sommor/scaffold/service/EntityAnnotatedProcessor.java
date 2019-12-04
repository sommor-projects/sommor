package com.sommor.scaffold.service;

import com.sommor.extensibility.config.Extension;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.scaffold.param.EntityInfoParam;
import com.sommor.view.FormView;
import com.sommor.view.PageView;
import com.sommor.view.form.EntityForm;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Extension(name = "基于Entity的业务逻辑处理器")
public interface EntityAnnotatedProcessor<
        Entity,
        Form extends EntityForm<Entity>,
        FormRenderParam extends EntityFormRenderParam,
        EntityInfo,
        InfoParam extends EntityInfoParam,
        EntityListItem,
        EntityQueryParam extends com.sommor.mybatis.query.EntityQueryParam> {

    default Entity findEntityByInfoParam(InfoParam param) {
        return null;
    }

    default void processOnInitQuery(EntityQueryParam param) {
    }

    default void processOnRenderListItem(Entity entity, EntityListItem item) {
    }

    default void processOnRenderList(PagingResult<EntityListItem> pagingResult, List<Entity> entities) {
    }

    default void processOnInfoRender(Entity entity, PageView<EntityInfo> pageView, InfoParam param) {
    }

    default void processOnFormRender(Entity entity, Form form, FormRenderParam param) {
    }

    default void processOnFormValidate(Form form) {
    }

    default void processOnFormSaving(Entity entity, Entity original, Form form) {
    }

    default void processOnSaving(Entity entity, Entity original) {
    }

    default void processOnSave(Entity entity, Entity original) {
    }

    default void processOnDelete(Entity entity) {
    }
}
