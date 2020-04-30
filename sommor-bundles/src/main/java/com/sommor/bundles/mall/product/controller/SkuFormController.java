package com.sommor.bundles.mall.product.controller;

import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.model.SkuForm;
import com.sommor.bundles.mall.product.model.SkuFormParam;
import com.sommor.bundles.mall.product.service.SkuFormService;
import com.sommor.bundles.mall.product.service.SkuService;
import com.sommor.core.component.form.FormController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
