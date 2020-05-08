package com.sommor.bundles.taxonomy.model;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.mybatis.query.Query;
import com.sommor.core.scaffold.param.EntityQueryParam;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
public class TaxonomyQueryParam extends EntityQueryParam {

    @Getter
    @Setter
    private String parent;

    @Override
    public void onModelQuery(Query query) {
        super.onModelQuery(query);

        if (StringUtils.isNotBlank(parent)) {
            TaxonomyKey key = TaxonomyKey.of(parent);
            query.where().condition()
                .and("type", key.getTaxonomyType())
                .and("parent", key.getName());
        } else {
            query.where().condition()
                    .and("type", TaxonomyEntity.ROOT)
                    .and("parent", TaxonomyEntity.ROOT);
        }
    }

    public void setTaxonomy(String taxonomy) {
        this.setParent(taxonomy);
    }
}
