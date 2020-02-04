package com.sommor.scaffold.fields.keywords;

import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.sql.select.Condition;
import com.sommor.core.view.field.FieldProcessor;
import com.sommor.core.view.field.FieldQueryContext;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Implement
public class KeywordsFieldProcessor implements FieldProcessor<KeywordsField> {

    @Override
    public void processOnQuery(KeywordsField keywordsField, FieldQueryContext ctx) {
        String keywords = ctx.getFieldValue();

        if (StringUtils.isNoneBlank(keywords)) {
            String[] fields = keywordsField.fields();
            if (fields.length > 0) {
                Condition condition = ctx.getQuery().where().condition();
                for (String fieldName : fields) {
                    condition.orLike(fieldName, keywords);
                }
            }
        }
    }
}
