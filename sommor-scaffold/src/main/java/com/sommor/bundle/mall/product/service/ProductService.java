package com.sommor.bundle.mall.product.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.bundle.mall.product.entity.SkuEntity;
import com.sommor.bundle.mall.product.model.*;
import com.sommor.bundle.mall.product.repository.ProductRepository;
import com.sommor.component.form.FormView;
import com.sommor.component.form.FormViewConfig;
import com.sommor.component.form.action.Add;
import com.sommor.model.Model;
import com.sommor.view.ViewEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@Service
public class ProductService {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ProductFormService productFormService;

    @Resource
    private SkuFormService skuFormService;

    @Resource
    private SkuService skuService;

    public FormView renderQuotationForm(ProductQuotationFormParam param) {
        ProductEntity productEntity = productRepository.findById(param.getProductId());
        if (null == productEntity) {
            throw new ErrorCodeException(ErrorCode.of("product.id.invalid", param.getProductId()));
        }

        if (! ProductTypeEnum.isSpu(productEntity.getProductType())) {
            throw new ErrorCodeException(ErrorCode.of("product.type.invalid", param.getProductId(), productEntity.getProductType()));
        }

        ProductQuotationFormRenderParam renderParam = new ProductQuotationFormRenderParam();
        renderParam.setShopId(param.getShopId());
        renderParam.setProductId(productEntity.getId());
        renderParam.setTaxonomyId(productEntity.getTaxonomyId());

        ProductQuotationForm form = new ProductQuotationForm();
        FormViewConfig fvc = new FormViewConfig();
        fvc.setModel(Model.of(form));
        fvc.setFormAction(Add.ACTION);

        return ViewEngine.render(fvc, Model.of(renderParam));
    }

    @Transactional
    public void saveProductQuotationForm(ProductQuotationForm form) {
        ProductForm productForm = new ProductForm();
        productForm.setSpuId(form.getProductId());
        productForm.setShopId(form.getShopId());
        ProductEntity productEntity = productFormService.saveEntityForm(productForm);

        SkuForm skuForm = new SkuForm();
        skuForm.setTitle(form.getTitle());
        skuForm.setProductId(productEntity.getId());
        skuForm.setCurrency(form.getCurrency());
        skuForm.setPrice(form.getAmount());

        skuForm.setTaxonomy(form.getTaxonomy());
        skuForm.setInventory(form.getInventory());
    }
}
