package com.sommor.bundles.mall.view;

import com.sommor.bundles.qrcode.fields.QrCodeField;
import com.sommor.bundles.taxonomy.view.fields.taxonomy.select.TaxonomySelect;
import com.sommor.bundles.taxonomy.view.fields.taxonomy.select.TaxonomySelectView;
import com.sommor.core.view.field.EntityForm;
import com.sommor.core.view.field.FieldRenderContext;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class ProductForm extends EntityForm {

    @Setter
    @Getter
    @NotNull
    private Integer shopId;

    @Setter
    @Getter
    private Integer spuId;

    @Setter
    @Getter
    @TaxonomySelect(type = "shop_catalog")
    private Integer catalogId;

    @Setter
    @Getter
    @QrCodeField
    private String qrCode;

    public void renderCatalogId(FieldRenderContext ctx) {
        TaxonomySelectView view = ctx.getFieldView();
        view.setGroup("shop_" + this.getShopId());
    }
}
