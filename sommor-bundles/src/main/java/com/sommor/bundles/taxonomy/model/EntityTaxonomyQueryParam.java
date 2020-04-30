package com.sommor.bundles.taxonomy.model;

import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.bundles.taxonomy.component.select.TaxonomySelectFieldConfig;
import com.sommor.bundles.taxonomy.component.select.TaxonomySelectField;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.core.model.Model;
import com.sommor.mybatis.query.Query;
import com.sommor.core.scaffold.param.EntityQueryParam;
import com.sommor.core.spring.ApplicationContextHolder;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.model.OnViewRender;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/28
 */
public class EntityTaxonomyQueryParam extends EntityQueryParam implements OnViewRender {

    @Getter
    @Setter
    @TaxonomySelectField(title = "分类", includeSelf = true)
    private String taxonomy;

    private String entityName;

    private TaxonomyRepository taxonomyRepository = ApplicationContextHolder.getBean(TaxonomyRepository.class);

    public EntityTaxonomyQueryParam(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public void onModelQuery(Query query) {
        super.onModelQuery(query);

        String entityName = this.entityName;
        String taxonomy = this.getTaxonomy();

        if (StringUtils.isNotBlank(taxonomy) && ! entityName.equals(taxonomy)) {
            TaxonomyKey taxonomyKey = TaxonomyKey.of(taxonomy, entityName);
            TaxonomyEntity taxonomyEntity = taxonomyRepository.findByKey(taxonomyKey);
            if (null == taxonomyEntity) {
                throw new ErrorCodeException(ErrorCode.of("subject.query.taxonomy.invalid", taxonomy, entityName));
            }

            query.where().condition()
                    .and("taxonomy", taxonomyKey.getKey());
        }
    }

    @Override
    public void onViewRender(Model model, ViewRenderContext ctx) {
        TaxonomySelectFieldConfig taxonomySelectFieldConfig = model.getField("taxonomy").getFieldConfig();
        taxonomySelectFieldConfig.setType(this.entityName);
        taxonomySelectFieldConfig.setTree(true);
    }
}
