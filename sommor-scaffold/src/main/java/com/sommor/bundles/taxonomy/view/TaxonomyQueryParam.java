package com.sommor.bundles.taxonomy.view;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.fields.conditional.Conditional;
import com.sommor.scaffold.param.EntitySearchParam;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
public class TaxonomyQueryParam extends EntitySearchParam {

    @Getter @Setter
    private Integer parentId;

    @Getter @Setter
    private String type;

    @Override
    public void onQuery(Query query) {
        super.onQuery(query);

        Integer parentId = 0;

        if (null != this.parentId) {
            parentId = this.parentId;
        } else if (StringUtils.isNotBlank(this.type)) {
            TaxonomyEntity entity = ApplicationContextHolder.getBean(TaxonomyRepository.class)
                    .findByType(this.type);
            parentId = entity.getId();
        }

        query.where("parentId", parentId);
    }
}
