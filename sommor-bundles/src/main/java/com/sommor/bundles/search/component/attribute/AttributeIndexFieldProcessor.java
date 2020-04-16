package com.sommor.bundles.search.component.attribute;

import com.alibaba.fastjson.JSON;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.search.entity.AttributeIndex;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import com.sommor.extensibility.config.Implement;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
@Implement
public class AttributeIndexFieldProcessor implements FieldFillProcessor<AttributeIndexFieldConfig> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Override
    public Object processOnFieldFill(AttributeIndexFieldConfig config, FieldFillContext ctx) {
        String value = ctx.getSourceModelFieldValue();
        if (StringUtils.isNotBlank(value)) {
            Class fieldType = ctx.getModelField().getType();
            if (fieldType == AttributeIndex.class) {
                TaxonomyEntity taxonomyEntity = taxonomyRepository.findByName(value, config.getEntityName());
                return convert(taxonomyEntity);
            } else if (fieldType == List.class) {
                Map<String, Object> attributes = JSON.parseObject(value);
                List<AttributeIndex> list = new ArrayList<>();
                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    String type = entry.getKey();
                    TaxonomyEntity taxonomyEntity = taxonomyRepository.findByType(type);
                    AttributeIndex attributeIndex = convert(taxonomyEntity);
                    Object o = entry.getValue();
                    List<TaxonomyEntity> taxonomyEntities = new ArrayList<>();
                    if (o instanceof Collection) {
                        for (String s : (Collection<String>) o) {
                            taxonomyEntities.add(taxonomyRepository.findByName(s, type));
                        }
                    } else {
                        taxonomyEntities.add(taxonomyRepository.findByName((String) o, type));
                    }
                    enrichAttributeIndex(attributeIndex, taxonomyEntities);
                    list.add(attributeIndex);
                }

                return list;
            }
        }

        return null;
    }

    private AttributeIndex convert(TaxonomyEntity taxonomyEntity) {
        AttributeIndex ai = new AttributeIndex();
        ai.setName(taxonomyEntity.isRoot() ? taxonomyEntity.getName() : taxonomyEntity.getType());

        enrichAttributeIndex(ai, taxonomyEntity);

        return ai;
    }

    private void enrichAttributeIndex(AttributeIndex attributeIndex, TaxonomyEntity taxonomyEntity) {
        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(taxonomyEntity);
        for (TaxonomyEntity entity : paths) {
            attributeIndex.addValue(entity.getName());
        }
    }

    private void enrichAttributeIndex(AttributeIndex attributeIndex, List<TaxonomyEntity> taxonomyEntities) {
        for (TaxonomyEntity taxonomyEntity : taxonomyEntities) {
            enrichAttributeIndex(attributeIndex, taxonomyEntity);
        }
    }

}
