package com.sommor.bundles.taxonomy.component.convert;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.ModelField;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/29
 */
@Implement
public class TaxonomyConvertFieldProcessor implements FieldFillProcessor<TaxonomyConvertFieldConfig> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Override
    public Object processOnFieldFill(TaxonomyConvertFieldConfig config, FieldFillContext ctx) {
        Object v = ctx.getModelField().getValue();
        if (null != v) {
            return v;
        }

        TaxonomyEntity taxonomyEntity = null;

        String taxonomyFieldName = config.getTaxonomyFieldName();
        ModelField taxonomyModelField = ctx.getModel().getField(taxonomyFieldName);
        if (null != taxonomyModelField) {
            Object value = taxonomyModelField.getValue();
            if (value instanceof String) {
                String s = (String) value;
                if (StringUtils.isNumeric(s)) {
                    taxonomyEntity = taxonomyRepository.findById(Integer.valueOf(s));
                } else {
                    taxonomyEntity = taxonomyRepository.findByKey((String) value);
                }
            } else if (value instanceof Integer) {
                Integer i = (Integer) value;
                taxonomyEntity = taxonomyRepository.findById(i);
            }
        }

        if (null != taxonomyEntity) {
            Class type = ctx.getModelField().getType();
            if (type == Integer.class) {
                return taxonomyEntity.getId();
            }
        }

        return null;
    }
}
