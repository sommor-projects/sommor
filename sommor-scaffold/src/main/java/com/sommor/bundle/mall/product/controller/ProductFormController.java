package com.sommor.bundle.mall.product.controller;

import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.bundle.mall.product.model.*;
import com.sommor.bundle.mall.product.service.ProductFormService;
import com.sommor.component.form.FormController;
import com.sommor.component.form.FormService;
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
