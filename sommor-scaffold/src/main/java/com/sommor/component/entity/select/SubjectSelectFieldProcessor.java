package com.sommor.component.entity.select;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.component.form.extension.FormFieldValidateProcessor;
import com.sommor.component.form.field.Option;
import com.sommor.curd.CurdManager;
import com.sommor.curd.query.FieldContext;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityManager;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.view.context.ViewRenderContext;
import com.sommor.view.extension.ViewRenderProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/26
 */
@Implement
public class SubjectSelectFieldProcessor implements
        ViewRenderProcessor<EntitySelectFieldConfig>,
        FormFieldValidateProcessor<EntitySelectFieldConfig> {

    @Override
    public void processOnFormValidate(EntitySelectFieldConfig config, FieldContext ctx) {
        Integer id = ctx.getFieldValue();
        if (null != id && id > 0) {
            String entityName = config.getEntityName();
            CurdRepository repository = CurdManager.getCurdRepository(entityName);
            BaseEntity entity = repository.findById(id);
            if (null == entity) {
                throw new ErrorCodeException(ErrorCode.of("subject.select.id.invalid", config.getEntityName(), id));
            }
        }
    }

    private static ExtensionExecutor<EntitySelectOptionParser> entitySelectOptionParser = ExtensionExecutor.of(EntitySelectOptionParser.class);

    @Override
    public void processOnViewRender(EntitySelectFieldConfig config, ViewRenderContext ctx) {
        EntitySelectView view  = ctx.getView();

        String entityName = config.getEntityName();
        view.setEntityName(entityName);

        Integer id = config.getValue();
        if (null != id && id > 0) {
            EntityDefinition ed = EntityManager.getDefinitionBySubject(entityName);

            CurdRepository curdRepository = CurdManager.getCurdRepository(ed.getEntityClass());
            BaseEntity entity = curdRepository.findById(id);
            Option option = entitySelectOptionParser.executeFirst(entity, ext -> ext.parseEntitySelectOption(entity));
            view.addOption(option);
        }
    }
}
