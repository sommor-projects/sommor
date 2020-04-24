package com.sommor.bundles.outline.service;

import com.sommor.bundles.mall.order.model.Order;
import com.sommor.bundles.mall.order.service.OrderService;
import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.bundles.outline.entity.OutlineOrderAccessKeyEntity;
import com.sommor.bundles.outline.entity.OutlineOrderEntity;
import com.sommor.bundles.outline.entity.OutlineServerEntity;
import com.sommor.bundles.outline.model.OutlineOrderCreateParam;
import com.sommor.bundles.outline.repository.OutlineOrderAccessKeyRepository;
import com.sommor.bundles.outline.repository.OutlineOrderRepository;
import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.core.utils.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Service
public class OutlineOrderService {

    @Resource
    private OrderService orderService;

    @Resource
    private OutlineServerService outlineServerService;

    @Resource
    private OutlineOrderRepository outlineOrderRepository;

    @Resource
    private OutlineOrderAccessKeyRepository outlineOrderAccessKeyRepository;

    public void createOutlineOrder(Order order) {
        createOutlineOrder(order, null);
    }

    public void createOutlineOrder(Order order, String serverId) {
        if (StringUtils.isBlank(serverId)) {
            OutlineServerEntity serverEntity = outlineServerService.selectOutlineServer(order);
            if (null != serverEntity) {
                serverId = serverEntity.getId();
            }
        }

        if (StringUtils.isBlank(serverId)) {
            throw new ErrorCodeException(ErrorCode.of("outline.order.create.server.invalid"));
        }

        OutlineOrderEntity outlineOrderEntity = new OutlineOrderEntity();
        outlineOrderEntity.setId(order.getId());
        outlineOrderEntity.setUserId(order.getUserId());

        Integer days = order.getProductAttributes().getInteger("outline_days");
        int now = DateTimeUtil.now();

        outlineOrderEntity.setStartTime(now);
        outlineOrderEntity.setEndTime(now + (days*86400));
        outlineOrderEntity.setAccessKeyCount(1);

        Integer accessKeyTotal = order.getProductAttributes().getInteger("outline_access_keys");
        outlineOrderEntity.setAccessKeyCount(accessKeyTotal == null ? 1 : accessKeyTotal);

        outlineOrderRepository.add(outlineOrderEntity);

        OutlineOrderAccessKeyEntity outlineOrderAccessKeyEntity = new OutlineOrderAccessKeyEntity();
        outlineOrderAccessKeyEntity.setOutlineServerId(serverId);
        outlineOrderAccessKeyEntity.setOrderId(order.getId());
        outlineOrderAccessKeyEntity.setUserId(order.getUserId());

        OutlineAccessKeyEntity outlineAccessKeyEntity = outlineServerService.createOutlineAccessKey(serverId);
        outlineOrderAccessKeyEntity.setId(outlineAccessKeyEntity.getId());

        outlineOrderAccessKeyRepository.add(outlineOrderAccessKeyEntity);

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
