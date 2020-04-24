package com.sommor.bundles.mall.product.service;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.model.ProductFormParam;
import com.sommor.bundles.mall.product.model.ProductSku;
import com.sommor.bundles.mall.product.model.ProductSkuForm;
import com.sommor.bundles.mall.product.model.ProductSkuFormParam;
import com.sommor.bundles.mall.product.model.SkuFormParam;
import com.sommor.core.component.form.FormService;
import com.sommor.core.component.form.FormView;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@Service
public class ProductSkuFormService implements FormService<ProductSku, ProductSkuForm, ProductSkuFormParam> {

    @Resource
    private ProductFormService productFormService;

    @Resource
    private SkuFormService skuFormService;

    public FormView renderForm(ProductSkuFormParam param) {
        ProductFormParam productFormParam = new ProductFormParam();
        productFormParam.setId(param.getProductId());
        productFormParam.setShopId(param.getShopId());
        productFormParam.setTaxonomy(productFormParam.getTaxonomy());
        FormView productFormView = productFormService.renderForm(productFormParam, "product");

        SkuFormParam skuFormParam = new SkuFormParam();
        skuFormParam.setShopId(param.getShopId());
        skuFormParam.setId(param.getSkuId());
        skuFormParam.setTaxonomy(param.getTaxonomy());
        FormView skuFormView = skuFormService.renderForm(skuFormParam, "sku");

        productFormView.addForm(skuFormView);

        return productFormView;
    }

    public ProductSku saveForm(ProductSkuForm productSkuForm) {
        ProductEntity productEntity = productFormService.saveForm(productSkuForm.getProduct());
        SkuEntity skuEntity = skuFormService.saveForm(productSkuForm.getSku());

        return ProductSku.of(productEntity, skuEntity);
    }
}
