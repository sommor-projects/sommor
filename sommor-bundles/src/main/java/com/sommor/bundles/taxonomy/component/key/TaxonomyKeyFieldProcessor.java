package com.sommor.bundles.taxonomy.component.key;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;

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
