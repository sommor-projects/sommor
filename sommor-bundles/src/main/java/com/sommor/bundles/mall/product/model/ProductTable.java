package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.qrcode.component.qrcode.QrCodeField;
import com.sommor.core.model.enricher.EntityModelEnricher;
import com.sommor.core.component.table.EntityTable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@EntityModelEnricher(entityName = "shop", entityIdFieldName = "shopId")
@EntityModelEnricher(entityName = "shop", entityIdFieldName = "shopId")
public class ProductTable extends EntityTable {

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String subTitle;

    @Setter
    @Getter
    private String cover;

    @Setter
    @Getter
    @QrCodeField
    private String qrCode;

    @Setter
    @Getter
    private Integer shopId;

    @Setter
    @Getter
    private String shopTitle;

    @Setter
    @Getter
    private String shopSubTitle;

    @Setter
    @Getter
    private Integer spuId;

    @Setter
    @Getter
    private Integer catalogId;
}
