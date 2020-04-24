package com.sommor.bundles.mall.product.controller;

import com.sommor.bundles.mall.product.model.ProductSku;
import com.sommor.bundles.mall.product.model.ProductSkuForm;
import com.sommor.bundles.mall.product.model.ProductSkuFormParam;
import com.sommor.bundles.mall.product.service.ProductSkuFormService;
import com.sommor.core.component.form.FormController;
import com.sommor.core.component.form.FormService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@RestController
@RequestMapping(value = "/api/product-sku")
public class ProductSkuFormController extends FormController<ProductSku, ProductSkuForm, ProductSkuFormParam> {

    public ProductSkuFormController(ProductSkuFormService productSkuFormService) {
        super(productSkuFormService);
    }

}
