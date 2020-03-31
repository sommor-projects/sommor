package com.sommor.bundle.mall.product.model;

import com.sommor.bundle.qrcode.component.qrcode.QrCodeField;
import com.sommor.model.enricher.EntityModelEnricher;
import com.sommor.component.table.EntityTable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
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
