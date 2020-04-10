package com.sommor.bundles.taxonomy.model;

import com.sommor.mybatis.query.Query;
import com.sommor.core.scaffold.param.EntityDetailParam;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
public class TaxonomyDetailParam extends EntityDetailParam {

    @Setter
    @Getter
    private String taxonomy;

    @Override
    public void onModelQuery(Query query) {
        super.onModelQuery(query);

        if ((this.getId() == null || this.getId() == 0) && StringUtils.isNotBlank(taxonomy)) {
            TaxonomyKey taxonomyKey = TaxonomyKey.of(taxonomy);
            query.where().condition()
                    .and("type", taxonomyKey.getType())
                    .and("name", taxonomyKey.getName());
        }
    }
}
