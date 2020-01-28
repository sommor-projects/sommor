package com.sommor.scaffold.fields.subject.select;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityManager;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.scaffold.view.Option;
import com.sommor.scaffold.view.field.FieldContext;
import com.sommor.scaffold.view.field.FieldRenderContext;
import com.sommor.scaffold.service.CurdManager;
import com.sommor.scaffold.view.field.FieldProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/26
 */
@Implement
public class SubjectSelectFieldProcessor implements FieldProcessor<SubjectSelectField> {

    @Override
    public void processOnFormRender(SubjectSelectField subjectSelectField, FieldRenderContext ctx) {
        SubjectSelectView view  = ctx.getFieldView();

        String subject = subjectSelectField.subject();

        view.setSubject(subject);

        Integer id = ctx.getFieldValue();

        if (null != id && id > 0) {
            EntityDefinition ed = EntityManager.getDefinitionBySubject(subject);

            CurdRepository curdRepository = CurdManager.getCurdRepository(ed.getEntityClass());
            BaseEntity entity = curdRepository.findById(id);

            Option option = CurdManager.getCurdService(ed.getEntityClass()).convertSelectOption(entity);
            view.addOption(option);
        }
    }

    @Override
    public void processOnFormValidate(SubjectSelectField subjectSelectField, FieldContext ctx) {
        Integer id = ctx.getFieldValue();
        if (null != id && id > 0) {
            String subject = subjectSelectField.subject();
            CurdRepository repository = CurdManager.getCurdRepository(subject);
            BaseEntity entity = repository.findById(id);
            if (null == entity) {
                throw new ErrorCodeException(ErrorCode.of("subject.select.id.invalid", subjectSelectField.subject(), id));
            }
        }
    }
}
