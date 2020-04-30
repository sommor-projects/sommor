package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.taxonomy.model.EntityTaxonomyQueryParam;
import com.sommor.core.component.keywords.KeywordsField;
import com.sommor.mybatis.query.Query;
import com.sommor.core.scaffold.param.EntityQueryParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class ProductQueryParam extends EntityTaxonomyQueryParam {

    @Getter
    @Setter
    private Integer shopId;

    @Getter
    @Setter
    private Integer catalogId;

    @Getter
    @Setter
    @KeywordsField(fields = {"title", "subTitle"})
    private String keywords;

    public ProductQueryParam() {
        super(ProductEntity.NAME);
    }

    @Override
    public void onModelQuery(Query query) {
        super.onModelQuery(query);

        Integer shopId = this.getShopId();
        if (null != shopId && shopId > 0) {
            query.where("shopId", shopId);
        }

        Integer catalogId = this.getCatalogId();
        if (null != catalogId && catalogId > 0) {
            query.where("catalogId", catalogId);
        }
    }
}
