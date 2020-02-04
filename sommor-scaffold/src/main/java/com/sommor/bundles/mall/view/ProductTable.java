package com.sommor.bundles.mall.view;

import com.sommor.bundles.qrcode.fields.QrCodeField;
import com.sommor.core.view.field.EntityTable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
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
    private Integer spuId;

    @Setter
    @Getter
    private Integer catalogId;
}
