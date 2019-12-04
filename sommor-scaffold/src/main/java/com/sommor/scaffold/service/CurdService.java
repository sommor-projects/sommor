package com.sommor.scaffold.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.extensibility.reducer.Reducers;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.ConfigurableEntity;
import com.sommor.mybatis.entity.TimedEntity;
import com.sommor.mybatis.query.EntityQueryParam;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.query.QueryFactory;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.field.type.Config;
import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.scaffold.param.EntityInfoParam;
import com.sommor.scaffold.utils.ClassAnnotatedTypeParser;
import com.sommor.view.FormView;
import com.sommor.view.PageView;
import com.sommor.view.form.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
public class CurdService<
        Entity extends BaseEntity,
        Form extends EntityForm<Entity>,
        FormRenderParam extends EntityFormRenderParam,
        EntityInfo,
        InfoParam extends EntityInfoParam,
        EntityListItem,
        QueryParam extends EntityQueryParam> {

    @Resource
    private CurdManager curdManager;

    private CurdRepository<Entity> curdRepository;

    private ExtensionExecutor<EntityAnnotatedProcessor> entityProcessor = ExtensionExecutor.of(EntityAnnotatedProcessor.class);

    private Class entityClass;

    private Class formClass;

    private Class listItemClass;

    public CurdService() {
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        if (null == classes || classes.length == 0) {
            throw new RuntimeException("unknown curd service annotated type, class: " + this.getClass().getName());
        }

        this.entityClass = classes[0];
        this.formClass = classes[1];
        this.listItemClass = classes[5];
    }

    @SuppressWarnings("unchecked")
    protected CurdRepository<Entity> curdRepository() {
        if (null == curdRepository) {
            if (null == entityClass) {
                throw new ErrorCodeException(ErrorCode.of("curd.service.entity.unknown", this.getClass().getName()));
            }

            curdRepository = curdManager.getCurdRepository(entityClass);
            if (null == curdRepository) {
                throw new ErrorCodeException(ErrorCode.of("curd.service.repository.unknown", this.getClass().getName()));
            }
        }

        return curdRepository;
    }

    @SuppressWarnings("unchecked")
    public PagingResult<EntityListItem> renderList(QueryParam param) {
        entityProcessor.run(this.entityClass, ext -> ext.processOnInitQuery(param));

        Query query = QueryFactory.fromParam(param);
        param.initQuery(query);

        PagingResult<Entity> pagingResult = curdRepository().findByPaging(query);

        PagingResult<EntityListItem> pagingListResult;

        if (listItemClass != entityClass) {
            List<EntityListItem> listItems = new ArrayList<>();
            for (Entity entity : pagingResult.getData()) {
                EntityListItem listItem;
                try {
                    listItem = (EntityListItem) listItemClass.newInstance();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }

                entityProcessor.run(
                        this.entityClass,
                        ext -> ext.processOnRenderListItem(entity, listItem)
                );

                listItems.add(listItem);
            }

            pagingListResult = PagingResult.of(pagingResult, listItems);
            entityProcessor.run(
                    this.entityClass,
                    ext -> ext.processOnRenderList(pagingListResult, pagingResult.getData())
            );
        } else {
            pagingListResult = (PagingResult<EntityListItem>) pagingResult;
        }

        return pagingListResult;
    }

    @SuppressWarnings("unchecked")
    public PageView<EntityInfo> renderInfo(InfoParam infoParam) {
        Entity entity;

        Integer id = infoParam.getId();
        if (null != id && id > 0) {
            entity = curdRepository().findById(infoParam.getId());
            if (null == entity) {
                throw new ErrorCodeException(ErrorCode.of("entity.info.id.invalid", id));
            }
        } else {
            entity = (Entity) entityProcessor.execute(
                    this.entityClass,
                    ext -> ext.findEntityByInfoParam(infoParam),
                    Reducers.firstNotNull()
            );

            if (null == entity) {
                throw new ErrorCodeException(ErrorCode.of("entity.info.absence"));
            }
        }

        PageView<EntityInfo> pageView = new PageView<>();

        entityProcessor.run(
                this.entityClass,
                ext -> ext.processOnInfoRender(entity, pageView, infoParam)
        );

        return pageView;
    }

    @SuppressWarnings("unchecked")
    public FormView renderForm(FormRenderParam param) {
        Form form;
        try {
            form = (Form) formClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        Integer id = param.getId();
        Entity entity;
        if (id != null && id > 0) {
            entity = curdRepository().findById(id);
            if (null == entity) {
                throw new ErrorCodeException(ErrorCode.of("entity.form.id.invalid", id));
            }

            form.fromEntity(entity);
        } else {
            entity = null;
        }

        FormView formView = new FormView(form);
        form.render(entity, formView, param);

        renderByFieldType(entity, form, formView);

        entityProcessor.run(
                this.entityClass,
                ext -> ext.processOnFormRender(entity, form, param)
        );

        formView.render();

        return formView;
    }

    @SuppressWarnings("unchecked")
    private void renderByFieldType(Entity entity, Form form, FormView formView) {
        FormDefinition fd = FormManager.getFormDefinition(form.getClass());
        for (FormFieldDefinition ffd : fd.getFields()) {
            if (ffd.isProcessFieldType()) {
                Object value;
                try {
                    value = ffd.getGetter().invoke(form);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
                ExtensionExecutor.of(FieldTypeProcessor.class)
                        .run(ffd.getField().getType(), ext -> ext.processOnFormRender(value, entity, form, formView));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Entity saveForm(Form form) {
        this.onFormValidate(form);

        Entity entity = form.toEntity();

        Integer id = entity.getId();
        boolean isNew = null == id || id <= 0;

        Entity originalEntity;
        if (isNew) {
            originalEntity = null;
        } else {
            originalEntity = curdRepository().findById(id);
            if (null == originalEntity) {
                throw new ErrorCodeException(ErrorCode.of("entity.update.id.invalid", entity.getClass().getSimpleName(), id));
            }
        }

        entityProcessor.run(
                this.entityClass,
                ext -> ext.processOnFormSaving(entity, originalEntity, form)
        );

        this.save(entity, originalEntity);

        saveByFieldT(form, entity, originalEntity);

        form.save(entity, originalEntity);

        return entity;
    }

    protected void onFormValidate(Form form) {
        form.validate();

        ExtensionExecutor.of(EntityAnnotatedProcessor.class)
                .run(this.entityClass, ext -> ext.processOnFormValidate(form));
    }

    @SuppressWarnings("unchecked")
    private void saveByFieldT(Form form, Entity entity, Entity originalEntity) {
        FormDefinition fd = FormManager.getFormDefinition(form.getClass());

        ExtensionExecutor<FieldViewProcessor> executor = ExtensionExecutor.of(FieldViewProcessor.class);
        for (FormFieldDefinition ffd : fd.getFields()) {
            Object v;
            try {
                v = ffd.getGetter().invoke(form);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

            if (ffd.isProcessFieldType()) {
                ExtensionExecutor.of(FieldTypeProcessor.class)
                        .run(ffd.getField().getType(), ext -> ext.processOnFormSave(v, entity, originalEntity, form));
            }

            ExtensionExecutor.of(FieldConfigProcessor.class)
                    .run(ffd.getConfig(), ext -> ext.processOnFormSave(ffd.getConfig(), v, entity, originalEntity, form));
        }
    }

    public Entity save(Entity entity) {
        return this.save(entity, null);
    }

    public Entity save(Entity entity, Entity originalEntity) {
        this.onSaving(entity, originalEntity);

        if (entity instanceof TimedEntity) {
            TimedEntity timedEntity = (TimedEntity) entity;
            int now = (int) (System.currentTimeMillis() / 1000);
            timedEntity.setUpdateTime(now);
            if (originalEntity == null) {
                timedEntity.setCreateTime(now);
            }
        }

        if (entity instanceof ConfigurableEntity) {
            ConfigurableEntity configurableEntity = (ConfigurableEntity) entity;
            if (null == configurableEntity.getConfig()) {
                configurableEntity.setConfig(new Config());
            }
        }


        if (null != originalEntity) {
            if (entity instanceof ConfigurableEntity) {
                ConfigurableEntity configurableEntity = (ConfigurableEntity) entity;
                Config originalConfig = ((ConfigurableEntity) originalEntity).getConfig();
                originalConfig.merge(configurableEntity.getConfig());
                configurableEntity.setConfig(originalConfig);
            }
        }

        curdRepository().save(entity);

        return entity;
    }

    protected void onSaving(Entity entity, Entity originalEntity) {
        ExtensionExecutor.of(EntityAnnotatedProcessor.class)
                .run(this.entityClass, ext -> ext.processOnSaving(entity, originalEntity));
    }

    public List<Entity> deleteBatch(List<Integer> ids) {
        List<Entity> list = new ArrayList<>();
        for (Integer id : ids) {
            Entity entity = this.delete(id);
            list.add(entity);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public Entity delete(Integer id) {
        Entity entity = curdRepository().findById(id);
        if (null == entity) {
            throw new ErrorCodeException(ErrorCode.of("entity.delete.id.invalid", id));
        }

        entityProcessor.run(
                this.entityClass,
                ext -> ext.processOnDelete(entity)
        );

        curdRepository().deleteById(id);

        return entity;
    }


}
