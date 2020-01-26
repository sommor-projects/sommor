package com.sommor.bundles.taxonomy.fields.taxonomy.paths;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.fields.taxonomy.type.TaxonomyInfo;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.extensibility.config.Implement;
import com.sommor.scaffold.view.field.FieldContext;
import com.sommor.scaffold.view.field.FieldProcessor;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/26
 */
@Implement
public class TaxonomyPathFieldProcessor implements FieldProcessor<TaxonomyPathField> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Override
    public Object processOnFill(TaxonomyPathField taxonomyPathField, FieldContext ctx) {
        String name = taxonomyPathField.name();
        Integer taxonomyId = ctx.getData().get(name);
        if (null == taxonomyId || taxonomyId <= 0) {
            return null;
        }

        List<TaxonomyEntity> taxonomyEntities = taxonomyRepository.findTaxonomyPaths(taxonomyId);

        return taxonomyEntities.stream()
                .map(p->new TaxonomyInfo(p))
                .collect(Collectors.toList());
    }
}
