package com.sommor.bundles.taxonomy.fields.subject.style;

import com.sommor.extensibility.config.Implement;
import com.sommor.scaffold.view.field.FieldRenderContext;
import com.sommor.scaffold.view.SelectView;
import com.sommor.scaffold.view.field.FieldProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Implement
public class SubjectTaxonomyStyleFieldProcessor implements FieldProcessor<SubjectTaxonomyStyleField> {

    @Override
    public void processOnFormRender(SubjectTaxonomyStyleField subjectTaxonomyStyleField, FieldRenderContext ctx) {
        SelectView selectView = ctx.getFieldView();
        selectView.addOption("select", "select");
        selectView.addOption("cascader", "cascader");
    }
}
