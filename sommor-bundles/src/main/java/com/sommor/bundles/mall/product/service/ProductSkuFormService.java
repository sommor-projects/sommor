package com.sommor.bundles.mall.product.service;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.model.ProductForm;
import com.sommor.bundles.mall.product.model.ProductFormParam;
import com.sommor.bundles.mall.product.model.ProductSku;
import com.sommor.bundles.mall.product.model.ProductSkuForm;
import com.sommor.bundles.mall.product.model.ProductSkuFormParam;
import com.sommor.bundles.mall.product.model.ProductTypeEnum;
import com.sommor.bundles.mall.product.model.SkuForm;
import com.sommor.bundles.mall.product.model.SkuFormParam;
import com.sommor.bundles.taxonomy.model.TaxonomyKey;
import com.sommor.core.component.form.FormService;
import com.sommor.core.component.form.FormView;
import com.sommor.core.utils.Converter;
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

    @Override
    public FormView renderForm(ProductSkuFormParam param, ProductSkuForm productSkuForm) {
        ProductFormParam productFormParam = new ProductFormParam();
        productFormParam.setId(Converter.toString(param.getId()));
        productFormParam.setShopId(param.getShopId());
        productFormParam.setTaxonomy(param.getTaxonomy());
        FormView productFormView = productFormService.renderForm(productFormParam, "product");

        SkuFormParam skuFormParam = new SkuFormParam();
        skuFormParam.setShopId(param.getShopId());
        skuFormParam.setId(param.getId());
        TaxonomyKey key = TaxonomyKey.of(param.getTaxonomy(), "product");
        skuFormParam.setTaxonomy(key.getKey());
        FormView skuFormView = skuFormService.renderForm(skuFormParam, "sku");

        return FormView.of(productFormView, skuFormView);
    }

    public ProductSku saveForm(ProductSkuForm productSkuForm) {
        ProductForm productForm = productSkuForm.getProduct();
        SkuForm skuForm = productSkuForm.getSku();

        productForm.setProductType(ProductTypeEnum.SALABLE.getType());
        skuForm.setProductType(ProductTypeEnum.SALABLE.getType());
        skuForm.setShopId(productSkuForm.getProduct().getShopId());

        ProductEntity productEntity = productFormService.saveForm(productSkuForm.getProduct());
        skuForm.setId(productEntity.getId());
        skuForm.setProductId(productEntity.getId());

        SkuEntity skuEntity = skuFormService.saveForm(productSkuForm.getSku());

        return ProductSku.of(productEntity, skuEntity);
    }
}
