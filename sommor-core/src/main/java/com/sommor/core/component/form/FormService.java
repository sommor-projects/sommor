package com.sommor.core.component.form;

import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
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
public class FormService<Entity extends BaseEntity, EntityForm, EntityFormParam> extends BaseCurdService<Entity> {

    private Class<EntityForm> formClass;

    private static final ExtensionExecutor<FormFieldValidateProcessor> formFieldValidateProcessor = ExtensionExecutor.of(FormFieldValidateProcessor.class);
    private static final ExtensionExecutor<FormFieldSavingProcessor> formFieldSavingProcessor = ExtensionExecutor.of(FormFieldSavingProcessor.class);
    private static final ExtensionExecutor<FormFieldSavedProcessor> formFieldSavedProcessor = ExtensionExecutor.of(FormFieldSavedProcessor.class);

    private static final ExtensionExecutor<FormFieldSavingInterceptor> formFieldSavingInterceptor = ExtensionExecutor.of(FormFieldSavingInterceptor.class);
    private static final ExtensionExecutor<FormFieldSavedInterceptor> formFieldSavedInterceptor = ExtensionExecutor.of(FormFieldSavedInterceptor.class);

    public FormService() {
        super();

        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.formClass = classes[1];
    }

    public FormService(Class<Entity> entityClass, Class formClass) {
        super(entityClass);
        this.formClass = formClass;
    }

    @SuppressWarnings("unchecked")
    public FormView renderEntityForm(EntityFormParam param) {
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
        return renderForm(form, sourceModel, formAction);
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
            return this.getEntityClass().newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public FormView renderForm(Object target, Object source, FormAction formAction) {
        return this.renderForm(target, null == source ? null : Model.of(source), formAction);
    }

    public FormView renderForm(Object target, Model sourceModel, FormAction formAction) {
        Model model = Model.of(target, RequestContext.get());
        FormViewConfig fvc = new FormViewConfig();
        fvc.setFormAction(formAction);
        fvc.setModel(model);

        FormView formView = ViewEngine.render(fvc, sourceModel);
        return formView;
    }

    @SuppressWarnings("unchecked")
    public Entity saveEntityForm(EntityForm target) {
        Model model = Model.of(target, RequestContext.get());
        this.onFormValidate(model);

        Entity entity = model.to(this.getEntityClass());
        Entity originalEntity = onGetOriginalEntity(model, entity);

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

    protected Entity onGetOriginalEntity(Model model, Entity entity) {
        Integer id = entity.getId();
        if (null != id && id > 0) {
            Entity originalEntity = curdRepository().findById(id);
            if (null == originalEntity) {
                throw new ErrorCodeException(ErrorCode.of("entity.update.id.invalid", entity.getClass().getSimpleName(), id));
            }

            return originalEntity;
        }

        return null;
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
