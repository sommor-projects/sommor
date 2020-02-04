package com.sommor.scaffold.fields.subject.name;

import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityManager;
import com.sommor.core.view.field.FieldRenderContext;
import com.sommor.core.view.SelectView;
import com.sommor.core.view.field.FieldProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Implement
public class SubjectNameSelectFieldProcessor implements FieldProcessor<SubjectNameSelectField> {

    @Override
    public void processOnFormRender(SubjectNameSelectField subjectNameSelectField, FieldRenderContext ctx) {
        SelectView selectView = ctx.getFieldView();

        for (EntityDefinition ed : EntityManager.all()) {
            if (ed.getSubjectName() != null) {
                selectView.addOption(ed.getSubjectName(), ed.getSubjectName());
            }
        }
    }
}
