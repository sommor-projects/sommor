package com.sommor.bundle.taxonomy.component.key;

import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.extensibility.config.Implement;
import com.sommor.model.fill.FieldFillContext;
import com.sommor.model.fill.FieldFillProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/4
 */
@Implement
public class TaxonomyKeyFieldProcessor implements FieldFillProcessor<TaxonomyKeyFieldConfig> {
    @Override
    public Object processOnFieldFill(TaxonomyKeyFieldConfig config, FieldFillContext ctx) {
        Object target = ctx.getSourceModel().getTarget();
        if (target instanceof TaxonomyEntity) {
            return ((TaxonomyEntity) target).getKey();
        }
        return null;
    }
}
