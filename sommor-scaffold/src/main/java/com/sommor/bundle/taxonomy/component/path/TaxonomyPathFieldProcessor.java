package com.sommor.bundle.taxonomy.component.path;

import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.model.TaxonomyInfo;
import com.sommor.bundle.taxonomy.repository.TaxonomyRepository;
import com.sommor.extensibility.config.Implement;
import com.sommor.model.fill.FieldFillContext;
import com.sommor.model.fill.FieldFillProcessor;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/26
 */
@Implement
public class TaxonomyPathFieldProcessor implements FieldFillProcessor<TaxonomyPathFieldConfig> {

    @Resource
    private TaxonomyRepository taxonomyRepository;


    @Override
    public Object processOnFieldFill(TaxonomyPathFieldConfig config, FieldFillContext ctx) {
        Integer taxonomyId = config.getTaxonomyId();
        if (null == taxonomyId || taxonomyId <= 0) {
            return null;
        }

        List<TaxonomyEntity> taxonomyEntities = taxonomyRepository.findTaxonomyPaths(taxonomyId);

        return taxonomyEntities.stream()
                .map(p->new TaxonomyInfo(p))
                .collect(Collectors.toList());
    }
}
