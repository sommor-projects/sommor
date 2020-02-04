package com.sommor.scaffold.fields.subject.count;

import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.core.curd.CurdManager;
import com.sommor.core.view.field.FieldContext;
import com.sommor.core.view.field.FieldProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
@Implement
public class SubjectCountFieldProcessor implements FieldProcessor<SubjectCountField> {

    @Override
    public Object processOnFill(SubjectCountField subjectCountField, FieldContext ctx) {
        Integer id = ctx.getFieldValue("id");
        CurdRepository repository = CurdManager.getCurdRepository(subjectCountField.subject());
        Query query = new Query().where(subjectCountField.subjectGroupFieldName(), id);
        Integer count = repository.count(query);
        return count;
    }
}
