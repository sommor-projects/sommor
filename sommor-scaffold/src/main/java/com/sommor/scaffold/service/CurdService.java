package com.sommor.scaffold.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.scaffold.context.RequestContext;
import com.sommor.scaffold.entity.EntityLifecycle;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.scaffold.utils.ClassAnnotatedTypeParser;
import com.sommor.scaffold.view.*;
import com.sommor.scaffold.view.DetailView;
import com.sommor.scaffold.view.field.*;
import com.sommor.scaffold.view.field.action.Add;
import com.sommor.scaffold.view.field.action.Edit;
import com.sommor.scaffold.view.field.action.FormAction;
import com.sommor.scaffold.view.field.action.Search;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
public class CurdService<
        Entity extends BaseEntity,
        EntityForm,
        FormParam,
        EntityDetail,
        DetailParam,
        EntityTable,
        SearchParam> {

    @Resource
    private CurdManager curdManager;

    private CurdRepository<Entity> curdRepository;

    private Class<Entity> entityClass;

    private Class formClass;

    private Class detailClass;

    private Class tableClass;

    public CurdService() {
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        if (null == classes || classes.length == 0) {
            throw new RuntimeException("unknown curd service annotated type, class: " + this.getClass().getName());
        }

        this.entityClass = classes[0];
        this.formClass = classes[1];
        this.detailClass = classes[3];
        this.tableClass = classes[5];
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
    public TableView renderTable(SearchParam searchParam) {
        Form form = new Form(searchParam);
        form.setAction(Search.ACTION);

        Query query = new Query();
        form.query(query);
        this.onTableQuery(searchParam, query);

        FormView formView = new FormView(form);
        formView.render();

        PagingResult<Entity> pagingResult = curdRepository().findByPaging(query);

        Object target;
        try {
            target = this.tableClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        Table table = new Table(target);

        TableView tableView = new TableView(table);
        tableView.setSearchForm(formView);

        tableView.render(pagingResult);

        return tableView;
    }

    protected void onTableQuery(SearchParam searchParam, Query query) {
    }

    public DetailView renderDetail(DetailParam detailParam) {
        Entity entity;

        Query query = new Query();

        if (detailParam instanceof OnQuery) {
            ((OnQuery) detailParam).onQuery(query);
        }

        if (! query.hasWhereClause()) {
            throw new ErrorCodeException(ErrorCode.of("entity.detail.param.invalid"));
        }

        entity = curdRepository().findFirst(query);
        if (null == entity) {
            throw new ErrorCodeException(ErrorCode.of("entity.detail.absence"));
        }

        Object target;
        try {
            target = this.detailClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        Detail detail = new Detail(target);
        detail.fill(entity);

        DetailView detailView = new DetailView(detail);
        detailView.render();

        return detailView;
    }

    @SuppressWarnings("unchecked")
    public FormView renderForm(FormParam param) {
        Query query = new Query();

        Fieldset fieldset = new Fieldset(param);
        fieldset.query(query);

        Object target;
        try {
            target = formClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        Form form = new Form(target);

        FormAction formAction;
        Object source;
        if (query.hasWhereClause()) {
            Entity entity = curdRepository().findFirst(query);
            if (null == entity) {
                throw new ErrorCodeException(ErrorCode.of("entity.form.param.invalid"));
            }
            formAction = Edit.ACTION;
            source = entity;
        } else {
            formAction = Add.ACTION;
            source = param;
        }

        form.setAction(formAction);
        form.fill(source);

        FormView formView = new FormView(form);
        formView.render();

        return formView;
    }

    @SuppressWarnings("unchecked")
    public Entity saveForm(EntityForm target) {
        Form form = new Form(target);

        this.onFormValidate(form);

        Entity entity = form.getTargetData().to(this.entityClass);

        boolean isNew = entity.isNew();

        Entity originalEntity;
        if (isNew) {
            originalEntity = null;
        } else {
            Integer id = entity.getId();
            originalEntity = curdRepository().findById(id);
            if (null == originalEntity) {
                throw new ErrorCodeException(ErrorCode.of("entity.update.id.invalid", entity.getClass().getSimpleName(), id));
            }
        }

        this.onFormSaving(form, entity, originalEntity);

        this.save(entity, originalEntity);

        this.onFormSaved(form, entity, originalEntity);

        return entity;
    }

    protected void onFormValidate(Form form) {
        FormValidateContext ctx = new FormValidateContext(RequestContext.get());
        ctx.setData(form.getTargetData());
        form.validate(ctx);
    }

    protected void onFormSaving(Form form, Entity entity, Entity originalEntity) {
        form.saving(entity, originalEntity);
    }

    @SuppressWarnings("unchecked")
    protected void onFormSaved(Form form, Entity entity, Entity originalEntity) {
        form.saved(entity, originalEntity);
    }

    public Entity save(Entity entity) {
        return this.save(entity, null);
    }

    public Entity save(Entity entity, Entity originalEntity) {
        this.onSaving(entity, originalEntity);

        EntityLifecycle entityLifecycle = entity instanceof EntityLifecycle ? (EntityLifecycle) entity : null;
        if (null != entityLifecycle) {
            entityLifecycle.onSaving(originalEntity);
        }

        curdRepository().save(entity);

        if (null != entityLifecycle) {
            entityLifecycle.onSaved(originalEntity);
        }

        this.onSaved(entity, originalEntity);

        return entity;
    }

    protected void onSaving(Entity entity, Entity originalEntity) {
    }

    protected void onSaved(Entity entity, Entity originalEntity) {
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

        this.onDelete(entity);

        curdRepository().deleteById(id);

        return entity;
    }

    protected void onDelete(Entity entity) {
    }
}
