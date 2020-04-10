package com.sommor.bundles.mall.product.controller;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.model.*;
import com.sommor.bundles.mall.product.service.ProductFormService;
import com.sommor.core.component.form.FormController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@RestController
@RequestMapping(value = "/api/product")
public class ProductFormController extends FormController<
        ProductEntity,
        ProductForm,
        ProductFormParam> {

    public ProductFormController(ProductFormService productFormService) {
        super(productFormService);
    }
}
