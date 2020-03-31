package com.sommor.bundle.mall.product.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.bundle.mall.product.model.*;
import com.sommor.bundle.mall.product.service.ProductService;
import com.sommor.component.form.FormView;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @ApiOperation(value = "商品报价")
    @RequestMapping(value = "/quotation/form", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<FormView> renderQuotationForm(@Validated ProductQuotationFormParam param) {
        FormView formView = productService.renderQuotationForm(param);
        return ApiResponse.success(formView);
    }
}
