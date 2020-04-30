package com.sommor.bundles.mall.order.service;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.mall.order.model.Buyer;
import com.sommor.bundles.mall.order.model.OrderForm;
import com.sommor.bundles.mall.order.model.OrderFormParam;
import com.sommor.bundles.mall.order.model.Seller;
import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.core.component.form.EntityFormService;
import com.sommor.core.generator.IdGenerator;
import com.sommor.core.model.Model;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@Service
public class OrderFormService extends EntityFormService<OrderEntity, OrderForm, OrderFormParam> {

    @Resource
    private IdGenerator orderIdGenerator;

    @Override
    protected void onFormValidate(Model model) {
        super.onFormValidate(model);
    }

    @Override
    protected void onFormSaving(Model model, OrderEntity entity, OrderEntity originalEntity) {
        super.onFormSaving(model, entity, originalEntity);

        if (null == originalEntity) {
            entity.setId(orderIdGenerator.generateId());

            entity.setPayStatus(OrderEntity.PAY_STATUS_UNPAID);
            entity.setStatus(OrderEntity.STATUS_CREATE);
            entity.setShippingStatus(OrderEntity.SHIPPING_STATUS_UNSHIPPED);

            this.beforeOrderCreate(model, entity);
        }
    }

    private void beforeOrderCreate(Model model, OrderEntity entity) {
        Buyer buyer = model.getExt(Buyer.class);
        if (null == buyer) {
            throw new ErrorCodeException(ErrorCode.of("order.create.buyer.is.null"));
        }
        entity.setBuyerId(buyer.getUserId());

        Seller seller = model.getExt(Seller.class);
        if (null == seller) {
            throw new ErrorCodeException(ErrorCode.of("order.create.seller.is.null"));
        }
        entity.setSellerId(seller.getUserId());

        ProductEntity productEntity = model.getExt(ProductEntity.class);
        if (null == productEntity) {
            throw new ErrorCodeException(ErrorCode.of("order.create.product.is.null"));
        }
        entity.setProductId(productEntity.getId());
        entity.setShopId(productEntity.getShopId());
        entity.setProductTitle(productEntity.getTitle());
        entity.setProductTaxonomy(productEntity.getTaxonomy());
        entity.setProductAttributes(productEntity.getAttributes());

        SkuEntity skuEntity = model.getExt(SkuEntity.class);
        if (null == skuEntity) {
            throw new ErrorCodeException(ErrorCode.of("order.create.sku.is.null"));
        }
        entity.setSkuId(skuEntity.getId());
        entity.setSkuAttributes(skuEntity.getAttributes());
        Long amount = skuEntity.getPrice() * entity.getBuyQuantity();
        entity.setAmount(amount);
        entity.setCurrency(skuEntity.getCurrency());

    }
}
