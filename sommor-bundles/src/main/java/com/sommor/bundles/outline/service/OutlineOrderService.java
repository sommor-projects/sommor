package com.sommor.bundles.outline.service;

import com.sommor.bundles.mall.order.model.Order;
import com.sommor.bundles.mall.order.service.OrderService;
import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.bundles.outline.entity.OutlineOrderAccessKeyEntity;
import com.sommor.bundles.outline.entity.OutlineOrderEntity;
import com.sommor.bundles.outline.model.OutlineOrderCreateParam;
import com.sommor.bundles.outline.model.OutlineServer;
import com.sommor.bundles.outline.repository.OutlineOrderAccessKeyRepository;
import com.sommor.bundles.outline.repository.OutlineOrderRepository;
import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.core.curd.CurdService;
import com.sommor.core.utils.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Service
public class OutlineOrderService extends CurdService<OutlineOrderEntity, Long> {

    @Resource
    private OrderService orderService;

    @Resource
    private OutlineServerService outlineServerService;

    @Resource
    private OutlineOrderAccessKeyService outlineOrderAccessKeyService;

    @Resource
    private OutlineOrderRepository outlineOrderRepository;

    public void createOutlineOrder(Order order) {
        createOutlineOrder(order, null);
    }

    public void createOutlineOrder(Order order, String serverId) {
        if (StringUtils.isBlank(serverId)) {
            OutlineServer server = outlineServerService.selectOutlineServer(order);
            if (null != server) {
                serverId = server.getId();
            }
        }

        if (StringUtils.isBlank(serverId)) {
            throw new ErrorCodeException(ErrorCode.of("outline.order.create.server.invalid"));
        }

        OutlineOrderEntity outlineOrderEntity = new OutlineOrderEntity();
        outlineOrderEntity.setId(order.getId());
        outlineOrderEntity.setUserId(order.getBuyerId());

        Integer days = order.getProductAttributes().getInteger("outline-days");
        int now = DateTimeUtil.now();

        outlineOrderEntity.setStartTime(now);
        outlineOrderEntity.setEndTime(now + (days*86400));
        outlineOrderEntity.setAccessKeyCount(1);

        Integer accessKeyTotal = order.getProductAttributes().getInteger("outline-access-keys");
        outlineOrderEntity.setAccessKeyTotal(accessKeyTotal == null ? 1 : accessKeyTotal);

        this.save(outlineOrderEntity);

        OutlineOrderAccessKeyEntity outlineOrderAccessKeyEntity = new OutlineOrderAccessKeyEntity();
        outlineOrderAccessKeyEntity.setOutlineServerId(serverId);
        outlineOrderAccessKeyEntity.setOrderId(order.getId());
        outlineOrderAccessKeyEntity.setUserId(order.getBuyerId());

        String accessKeyName = order.getBuyer().getNickName();
        OutlineAccessKeyEntity outlineAccessKeyEntity = outlineServerService.createOutlineAccessKey(serverId, accessKeyName);
        outlineOrderAccessKeyEntity.setId(outlineAccessKeyEntity.getId());

        outlineOrderAccessKeyService.save(outlineOrderAccessKeyEntity);

        orderService.ship(order.getId());
    }

    public void createOutlineOrder(OutlineOrderCreateParam param) {
        Order order = orderService.findOrder(param.getOrderId());
        if (null == order) {
            throw new ErrorCodeException(ErrorCode.of("order.id.invalid", param.getOrderId()));
        }

        String serverId = param.getServerId();

        createOutlineOrder(order, serverId);
    }
}
