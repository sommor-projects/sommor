package com.sommor.bundles.taxonomy.component.path;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.model.TaxonomyInfo;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import org.apache.commons.lang3.StringUtils;

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
        String taxonomy = config.getTaxonomy();
        if (StringUtils.isBlank(taxonomy)) {
            return null;
        }

        String type = config.getType();

        List<TaxonomyEntity> taxonomyEntities = taxonomyRepository.findTaxonomyPaths(taxonomy, type);

        return taxonomyEntities.stream()
                .map(p->new TaxonomyInfo(p))
                .collect(Collectors.toList());
    }
}
