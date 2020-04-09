package com.sommor.bundle.taxonomy.model;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundle.taxonomy.component.select.TaxonomySelectFieldConfig;
import com.sommor.bundle.taxonomy.component.select.TaxonomySelectField;
import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.repository.TaxonomyRepository;
import com.sommor.model.Model;
import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.param.EntityQueryParam;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import com.sommor.view.context.ViewRenderContext;
import com.sommor.view.model.OnViewRender;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
            TaxonomyEntity taxonomyEntity = taxonomyRepository.findByName(taxonomy, entityName);
            if (null == taxonomyEntity) {
                throw new ErrorCodeException(ErrorCode.of("subject.query.taxonomy.invalid", taxonomy, entityName));
            }

            TaxonomyKey taxonomyKey = TaxonomyKey.of(taxonomyEntity);
            query.where().condition()
                    .and("taxonomy", taxonomyKey.getName());
        }
    }

    @Override
    public void onViewRender(Model model, ViewRenderContext ctx) {
        TaxonomySelectFieldConfig taxonomySelectFieldConfig = model.getField("taxonomy").getFieldConfig();
        taxonomySelectFieldConfig.setType(this.entityName);
        taxonomySelectFieldConfig.setTree(true);
    }
}
