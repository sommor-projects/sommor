package com.sommor.bundles.mall.product.controller;

import com.sommor.core.api.response.ApiResponse;
import com.sommor.bundles.mall.product.model.*;
import com.sommor.bundles.mall.product.service.ProductService;
import com.sommor.core.component.form.FormView;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
