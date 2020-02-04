package com.sommor.bundles.taxonomy.view.fields.taxonomy.type;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.view.field.FieldContext;
import com.sommor.core.view.field.FieldProcessor;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/25
 */
@Implement
public class TaxonomyTypeFieldProcessor implements FieldProcessor<TaxonomyTypeField> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Override
    public Object processOnFill(TaxonomyTypeField taxonomyTypeField, FieldContext ctx) {
        String fieldName = taxonomyTypeField.name();
        if (StringUtils.isBlank(fieldName)) {
            fieldName = ctx.getDefinition().getName();
        }

        Object type = ctx.getData().get(fieldName);
        if (null == type) {
            return null;
        }

        TaxonomyEntity entity = null;
        if (type instanceof String) {
            entity = taxonomyRepository.findByName((String) type);
        } else if (type instanceof Integer) {
            Integer id = (Integer) type;
            entity = taxonomyRepository.findById(id);

            if (0 == id) {
                Object target = ctx.getData().getTarget();
                if (target instanceof TaxonomyEntity) {
                    entity = (TaxonomyEntity) target;
                }
            }
        }

        Class fieldType = ctx.getDefinition().getField().getType();
        if (fieldType == TaxonomyInfo.class) {
            return null == entity ? null : new TaxonomyInfo(entity);
        }

        return null;
    }
}
