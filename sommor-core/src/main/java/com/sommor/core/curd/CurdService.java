package com.sommor.core.curd;

import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.core.context.RequestContext;
import com.sommor.core.curd.delete.EntityDeleteInterceptor;
import com.sommor.core.curd.query.FieldQueryContext;
import com.sommor.core.curd.query.FieldQueryInterceptor;
import com.sommor.core.curd.query.FieldQueryProcessor;
import com.sommor.core.curd.query.OnModelQuery;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.core.model.Model;
import com.sommor.core.model.ModelField;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.definition.EntityManager;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.mybatis.query.Query;
import com.sommor.core.scaffold.entity.EntityLifecycle;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
public class CurdService<Entity extends BaseEntity> extends BaseCurdService<Entity> implements ApplicationListener<ContextRefreshedEvent> {

    public CurdService() {
        super();
    }

    private static ExtensionExecutor<com.sommor.core.curd.query.FieldQueryProcessor> fieldQueryProcessor = ExtensionExecutor.of(FieldQueryProcessor.class);
    private static ExtensionExecutor<com.sommor.core.curd.query.FieldQueryInterceptor> fieldQueryInterceptor = ExtensionExecutor.of(FieldQueryInterceptor.class);

    private static ExtensionExecutor<EntityDeleteInterceptor> entityDeleteInterceptor = ExtensionExecutor.of(EntityDeleteInterceptor.class);

    public Entity queryFirst(Object param) {
        Model paramModel = Model.of(param);
        return this.queryFirst(paramModel);
    }

    public Entity queryFirst(Model paramModel) {
        Query query = new Query();
        this.enrichQuery(paramModel, query);

        if (query.hasWhereClause()) {
            return curdRepository().findFirst(query);
        }

        return null;
    }

    public PagingResult<Entity> queryByPaging(Object param) {
        Model paramModel = Model.of(param);;
        return this.queryByPaging(paramModel);
    }

    public PagingResult<Entity> queryByPaging(Model paramModel) {
        Query query = new Query();
        this.enrichQuery(paramModel, query);
        return curdRepository().findByPaging(query);
    }

    private void enrichQuery(Model model, Query query) {
        Object target = model.getTarget();
        if (target instanceof OnModelQuery) {
            ((OnModelQuery) target).onModelQuery(query);
        }

        for (ModelField modelField: model.getFields()) {
            com.sommor.core.curd.query.FieldQueryContext ctx = new FieldQueryContext(RequestContext.get());
            ctx.setModel(model);
            ctx.setModelField(modelField);
            ctx.setQuery(query);

            if (null != modelField.getFieldConfig()) {
                Annotation annotation = modelField.getFieldConfig().annotation();
                if (null != annotation) {
                    fieldQueryProcessor.run(
                            annotation,
                            ext -> ext.processOnQuery(annotation, ctx)
                    );
                }
            }

            fieldQueryInterceptor.run(ext -> ext.interceptOnQuery(ctx));
        }
    }

    public Entity update(Entity entity) {
        Entity originalEntity = onGetOriginalEntity(entity);
        return this.save(entity, originalEntity, false);
    }

    public Entity save(Entity entity) {
        Entity originalEntity = onGetOriginalEntity(entity);
        return this.save(entity, originalEntity);
    }

    public Entity save(Entity entity, Entity originalEntity) {
        return this.save(entity, originalEntity, null == originalEntity);
    }

    private Entity save(Entity entity, Entity originalEntity, boolean insert) {
        this.onValidate(entity, originalEntity);

        this.onSaving(entity, originalEntity);

        EntityLifecycle entityLifecycle = entity instanceof EntityLifecycle ? (EntityLifecycle) entity : null;
        if (null != entityLifecycle) {
            entityLifecycle.onSaving(originalEntity);
        }

        if (insert) {
            curdRepository().add(entity);
        } else {
            curdRepository().update(entity);
        }

        if (null != entityLifecycle) {
            entityLifecycle.onSaved(originalEntity);
        }

        this.onSaved(entity, originalEntity);

        return entity;
    }

    public Entity onGetOriginalEntity(Entity entity) {
        Long id = entity.getId();
        if (null != id && id > 0) {
            Entity originalEntity = curdRepository().findById(id);
            if (null == originalEntity) {
                throw new ErrorCodeException(ErrorCode.of("entity.update.id.invalid", entity.getClass().getSimpleName(), id));
            }

            return originalEntity;
        }

        return null;
    }

    protected void onValidate(Entity entity, Entity originalEntity) {
    }

    protected void onSaving(Entity entity, Entity originalEntity) {
    }

    protected void onSaved(Entity entity, Entity originalEntity) {
    }

    public List<Entity> deleteBatch(List<Long> ids) {
        List<Entity> list = new ArrayList<>();
        for (Long id : ids) {
            Entity entity = this.delete(id);
            list.add(entity);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public Entity delete(Long id) {
        Entity entity = curdRepository().findById(id);
        if (null == entity) {
            throw new ErrorCodeException(ErrorCode.of("entity.delete.id.invalid", id));
        }

        return this.delete(entity);
    }

    public Entity delete(Entity entity) {
        this.onDelete(entity);
        curdRepository().deleteById(entity.getId());
        return entity;
    }

    protected void onDelete(Entity entity) {
        entityDeleteInterceptor.run(
                ext -> ext.interceptOnEntityDelete(entity)
        );
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        EntityManager.getDefinition(this.getEntityClass());
    }
}
