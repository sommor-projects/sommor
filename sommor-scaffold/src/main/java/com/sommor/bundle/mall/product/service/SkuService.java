package com.sommor.bundle.mall.product.service;

import com.sommor.bundle.mall.product.repository.ProductRepository;
import com.sommor.bundle.mall.product.repository.SkuRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
@Service
public class SkuService {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private SkuRepository skuRepository;

    //protected void onInitFormRender(Form form, Model sourceModel) {
       /* Object source = sourceModel.getTarget();

        if (source instanceof SkuFormParam) {
            SkuFormParam skuFormParam = (SkuFormParam) source;

            ProductEntity productEntity = sourceModel.getExt(ProductEntity.class);
            if (null == productEntity) {
                throw new ErrorCodeException(ErrorCode.of("sku.form.product.absent", skuFormParam.getProductId()));
            }

            skuFormParam.setTaxonomyId(productEntity.getTaxonomyId());
        }*/
    //}

}
