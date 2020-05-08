package com.sommor.bundles.mall.order.service;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.mall.order.model.Buyer;
import com.sommor.bundles.mall.order.model.OrderForm;
import com.sommor.bundles.mall.order.model.OrderFormParam;
import com.sommor.bundles.mall.order.model.OrderStatusEnum;
import com.sommor.bundles.mall.order.model.PayStatusEnum;
import com.sommor.bundles.mall.order.model.Seller;
import com.sommor.bundles.mall.order.model.ShippingStatusEnum;
import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.model.Product;
import com.sommor.bundles.mall.product.model.Sku;
import com.sommor.bundles.taxonomy.component.attribute.Attributes;
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
            Long orderId = orderIdGenerator.generateId();
            entity.setId(orderId);
            if (null == entity.getRefId()) {
                entity.setRefId(orderId);
            }

            entity.setPayStatus(PayStatusEnum.UNPAID.getCode());
            entity.setStatus(OrderStatusEnum.CREATED.getCode());
            entity.setShippingStatus(ShippingStatusEnum.UNSHIPPED.getCode());

            this.beforeOrderCreate(model, entity);
        }
    }

    private void beforeOrderCreate(Model model, OrderEntity entity) {
        OrderForm orderForm = model.getTarget();

        Attributes attributes = new Attributes();
        attributes.addAttributes(orderForm.getAttributes());

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

        Product product = model.getExt(Product.class);
        if (null == product) {
            throw new ErrorCodeException(ErrorCode.of("order.create.product.is.null"));
        }
        entity.setProductId(product.getId());
        entity.setShopId(product.getShopId());
        entity.setProductTitle(product.getTitle());
        entity.setTaxonomy(product.getTaxonomy());
        attributes.addAttributes(product.getAttributes());

        Sku sku = model.getExt(Sku.class);
        if (null == sku) {
            throw new ErrorCodeException(ErrorCode.of("order.create.sku.is.null"));
        }
        entity.setSkuId(sku.getId());
        Long amount = sku.getPrice() * entity.getBuyQuantity();
        entity.setAmount(amount);
        entity.setCurrency(sku.getCurrency());
        attributes.addAttributes(sku.getAttributes());

        entity.setAttributes(attributes.toJSONString());
    }
}
