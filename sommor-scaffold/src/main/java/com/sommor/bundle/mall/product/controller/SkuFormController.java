package com.sommor.bundle.mall.product.controller;

import com.sommor.bundle.mall.product.entity.SkuEntity;
import com.sommor.bundle.mall.product.model.SkuForm;
import com.sommor.bundle.mall.product.model.SkuFormParam;
import com.sommor.bundle.mall.product.service.SkuFormService;
import com.sommor.component.form.FormController;
import com.sommor.component.form.FormService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@RestController
@RequestMapping(value = "/api/sku")
public class SkuFormController extends FormController<
        SkuEntity,
        SkuForm,
        SkuFormParam> {

    public SkuFormController(SkuFormService skuFormService) {
        super(skuFormService);
    }
}
