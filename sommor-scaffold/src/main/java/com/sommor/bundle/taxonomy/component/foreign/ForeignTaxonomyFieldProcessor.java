package com.sommor.bundle.taxonomy.component.foreign;

import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.model.TaxonomyInfo;
import com.sommor.bundle.taxonomy.repository.TaxonomyRepository;
import com.sommor.extensibility.config.Implement;
import com.sommor.model.fill.FieldFillContext;
import com.sommor.model.fill.FieldFillProcessor;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/25
 */
@Implement
public class ForeignTaxonomyFieldProcessor implements FieldFillProcessor<TaxonomyForeignFieldConfig> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Override
    public Object processOnFieldFill(TaxonomyForeignFieldConfig config, FieldFillContext ctx) {
        String taxonomy = config.getTaxonomy();
        String type = config.getType();

        TaxonomyEntity entity = null;

        if (StringUtils.isNotBlank(taxonomy)) {
            if (ctx.getSourceModel().getTarget() instanceof TaxonomyEntity) {
                TaxonomyEntity e = ctx.getSourceModel().getTarget();
                if (e.equals(taxonomy, type)) {
                    entity = e;
                }
            }

            if (null == entity) {
                entity = taxonomyRepository.findByName(taxonomy, type);
            }
        }


        if (null != entity) {
            Class fieldType = ctx.getModelField().getType();
            if (fieldType == TaxonomyInfo.class) {
                return new TaxonomyInfo(entity);
            }
        }

        return null;
    }

}
