package com.sommor.bundles.taxonomy.view;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.param.EntityQueryParam;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
public class TaxonomyQueryParam extends EntityQueryParam {

    @Getter @Setter
    private Integer parentId;

    @Getter @Setter
    private Integer taxonomyId;

    @Getter @Setter
    private String taxonomy;

    private TaxonomyRepository taxonomyRepository = ApplicationContextHolder.getBean(TaxonomyRepository.class);

    @Override
    public void onQuery(Query query) {
        super.onQuery(query);

        Integer parentId = this.getParentId();
        String taxonomy = this.getTaxonomy();

        if (null == parentId) {
            if (null != taxonomyId) {
                parentId = taxonomyId;
            } else if (StringUtils.isNotBlank(taxonomy)) {
                if (StringUtils.isNumeric(taxonomy)) {
                    parentId = Integer.valueOf(taxonomy);
                } else {
                    TaxonomyEntity entity = taxonomyRepository.findByName(taxonomy);
                    parentId = entity.getId();
                }
            }
        }

        if (null != parentId) {
            query.where("parentId", parentId);
        }
    }
}
