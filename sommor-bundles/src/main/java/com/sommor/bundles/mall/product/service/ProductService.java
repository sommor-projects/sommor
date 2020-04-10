package com.sommor.bundles.mall.product.service;

import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.model.*;
import com.sommor.bundles.mall.product.repository.ProductRepository;
import com.sommor.core.component.form.FormView;
import com.sommor.core.component.form.FormViewConfig;
import com.sommor.core.component.form.action.Add;
import com.sommor.core.curd.CurdService;
import com.sommor.core.model.Model;
import com.sommor.core.view.ViewEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@Service
public class ProductService extends CurdService<ProductEntity> {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ProductFormService productFormService;

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
        renderParam.setTaxonomy(productEntity.getTaxonomy());

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
