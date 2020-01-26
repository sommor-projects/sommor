package com.sommor.bundles.taxonomy.view;

import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.param.EntityDetailParam;
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
    private String type;

    @Override
    public void onQuery(Query query) {
        super.onQuery(query);

        if ((this.getId() == null || this.getId() == 0) && StringUtils.isNotBlank(type)) {
            query.where().condition()
                    .and("typeId", 0)
                    .and("slug", type);
        }
    }
}
