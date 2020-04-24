package com.sommor.core.component.form;

import com.sommor.core.component.form.action.Add;
import com.sommor.core.component.form.action.Edit;
import com.sommor.core.component.form.action.FormAction;
import com.sommor.core.component.form.extension.*;
import com.sommor.core.context.RequestContext;
import com.sommor.core.curd.BaseCurdService;
import com.sommor.core.curd.query.FieldContext;
import com.sommor.core.utils.ClassAnnotatedTypeParser;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.core.model.Model;
import com.sommor.core.model.ModelField;
import com.sommor.core.model.ModelManager;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.core.scaffold.spring.ApplicationContextHolder;
import com.sommor.core.view.ViewEngine;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/14
 */
public class EntityFormService<Entity extends BaseEntity, EntityForm, EntityFormParam>
        extends BaseCurdService<Entity>
        implements FormService<Entity, EntityForm, EntityFormParam> {

    private Class<EntityForm> formClass;

    private static final ExtensionExecutor<FormFieldValidateProcessor> formFieldValidateProcessor = ExtensionExecutor.of(FormFieldValidateProcessor.class);
    private static final ExtensionExecutor<FormFieldSavingProcessor> formFieldSavingProcessor = ExtensionExecutor.of(FormFieldSavingProcessor.class);
    private static final ExtensionExecutor<FormFieldSavedProcessor> formFieldSavedProcessor = ExtensionExecutor.of(FormFieldSavedProcessor.class);

    private static final ExtensionExecutor<FormFieldSavingInterceptor> formFieldSavingInterceptor = ExtensionExecutor.of(FormFieldSavingInterceptor.class);
    private static final ExtensionExecutor<FormFieldSavedInterceptor> formFieldSavedInterceptor = ExtensionExecutor.of(FormFieldSavedInterceptor.class);

    public EntityFormService() {
        super();

        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.formClass = classes[1];
    }

    public EntityFormService(Class<Entity> entityClass, Class formClass) {
        super(entityClass);
        this.formClass = formClass;
    }

    public FormView renderForm(EntityFormParam param) {
        return this.renderForm(param, null);
    }

    @SuppressWarnings("unchecked")
    public FormView renderForm(EntityFormParam param, String formName) {
        Model paramModel = Model.of(param);

        Entity entity = (Entity) curdService().queryFirst(paramModel);

        FormAction formAction;
        Model sourceModel;
        if (entity != null) {
            formAction = Edit.ACTION;
            sourceModel = Model.of(entity);
        } else {
            formAction = Add.ACTION;
            paramModel.fill();
            sourceModel = paramModel;
        }

        EntityForm form = newForm();

        this.onEntityFormRender(form, param, entity);

        return renderForm(formName, form, sourceModel, formAction);
    }

    protected void onEntityFormRender(EntityForm form, EntityFormParam formParam, Entity entity) {

    }

    private EntityForm newForm() {
        try {
            return formClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private Entity newEntity() {
        try {
            return (Entity) this.getEntityClass().newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public FormView renderForm(Object target, Object source, FormAction formAction) {
        return this.renderForm(null, target, null == source ? null : Model.of(source), formAction);
    }

    public FormView renderForm(String formName, Object target, Model sourceModel, FormAction formAction) {
        Model model = Model.of(target, RequestContext.get());
        model.setName(formName);
        FormViewConfig fvc = new FormViewConfig();
        fvc.setFormAction(formAction);
        fvc.setModel(model);

        return ViewEngine.render(fvc, sourceModel);
    }

    @SuppressWarnings("unchecked")
    public Entity saveForm(EntityForm target) {
        Model model = Model.of(target, RequestContext.get());
        this.onFormValidate(model);

        Entity entity = (Entity) model.to(this.getEntityClass());
        Entity originalEntity = (Entity) curdService().onGetOriginalEntity(entity);

        DataSourceTransactionManager dataSourceTransactionManager = ApplicationContextHolder.getBean(DataSourceTransactionManager.class);
        TransactionDefinition transactionDefinition = ApplicationContextHolder.getBean(TransactionDefinition.class);
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            this.onFormSaving(model, entity, originalEntity);
            curdService().save(entity, originalEntity);
            this.onFormSaved(model, entity, originalEntity);

            dataSourceTransactionManager.commit(transactionStatus);
        } catch (Throwable e) {
            dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        }

        return entity;
    }

    protected void onFormValidate(Model model) {
        for (ModelField modelField: model.getFields()) {
            if (null != modelField.getFieldConfig()) {
                FieldContext ctx = new FieldContext(ModelManager.getModelFieldSubContext(modelField));
                ctx.setModelField(modelField);
                ctx.setModel(model);

                formFieldValidateProcessor.run(
                        modelField.getFieldConfig(),
                        ext -> ext.processOnFormValidate(modelField.getFieldConfig(), ctx)
                );
            }
        }
    }

    protected void onFormSaving(Model model, Entity entity, Entity originalEntity) {
        for (ModelField modelField: model.getFields()) {
            FieldSaveContext ctx = new FieldSaveContext(ModelManager.getModelFieldSubContext(modelField));
            ctx.setModelField(modelField);
            ctx.setModel(model);
            ctx.setEntity(entity);
            ctx.setOriginal(originalEntity);

            if (null != modelField.getFieldConfig()) {
                formFieldSavingProcessor.run(
                        modelField.getFieldConfig(),
                        ext -> ext.processOnFormSaving(modelField.getFieldConfig(), ctx)
                );
            }

            formFieldSavingInterceptor.run(
                    ext -> ext.interceptOnFormSaving(ctx)
            );
        }
    }

    @SuppressWarnings("unchecked")
    protected void onFormSaved(Model model, Entity entity, Entity originalEntity) {
        for (ModelField modelField: model.getFields()) {;
            FieldSaveContext ctx = new FieldSaveContext(ModelManager.getModelFieldSubContext(modelField));
            ctx.setModelField(modelField);
            ctx.setModel(model);
            ctx.setEntity(entity);
            ctx.setOriginal(originalEntity);

            if (null != modelField.getFieldConfig()) {
                formFieldSavedProcessor.run(
                        modelField.getFieldConfig(),
                        ext -> ext.processOnFormSaved(modelField.getFieldConfig(), ctx)
                );
            }

            formFieldSavedInterceptor.run(
                    ext -> ext.interceptOnFormSaved(ctx)
            );
        }
    }
}
