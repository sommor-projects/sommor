package com.sommor.bundles.mall.view;

import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.param.EntityQueryParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class ProductQueryParam extends EntityQueryParam {

    @Getter
    @Setter
    private Integer shopId;

    @Getter
    @Setter
    private Integer catalogId;

    @Override
    public void onQuery(Query query) {
        super.onQuery(query);

        query.leftJoin("spu", "spu.id=shop_spu.spu_id",
                        "title", "subTitle", "cover"
                );

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
