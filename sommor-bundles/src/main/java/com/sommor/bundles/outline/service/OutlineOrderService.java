package com.sommor.bundles.outline.service;

import com.sommor.bundles.mall.order.model.Order;
import com.sommor.bundles.mall.order.service.OrderService;
import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.bundles.outline.entity.OutlineOrderAccessKeyEntity;
import com.sommor.bundles.outline.entity.OutlineOrderEntity;
import com.sommor.bundles.outline.model.OutlineOrderCreateParam;
import com.sommor.bundles.outline.model.OutlineServer;
import com.sommor.bundles.outline.repository.OutlineOrderRepository;
import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.core.curd.CurdService;
import com.sommor.core.utils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

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
        outlineOrderEntity.setRenewOrderId(order.getId());
        outlineOrderEntity.setBuyerId(order.getBuyerId());

        String days = order.getAttributes().getString("outline-days");
        long[] times = calculateOutlineOrderStartAndExpireTime(days);
        outlineOrderEntity.setStartTime(times[0]);
        outlineOrderEntity.setEndTime(times[1]);

        outlineOrderEntity.setAccessKeyCount(1);

        Integer accessKeyTotal = order.getAttributes().getInteger("outline-access-keys");
        outlineOrderEntity.setAccessKeyTotal(accessKeyTotal == null ? 1 : accessKeyTotal);

        this.save(outlineOrderEntity);

        OutlineOrderAccessKeyEntity outlineOrderAccessKeyEntity = new OutlineOrderAccessKeyEntity();
        outlineOrderAccessKeyEntity.setOutlineServerId(serverId);
        outlineOrderAccessKeyEntity.setOrderId(order.getId());
        outlineOrderAccessKeyEntity.setBuyerId(order.getBuyerId());

        String accessKeyName = order.getBuyer().getNickName();
        OutlineAccessKeyEntity outlineAccessKeyEntity = outlineServerService.createOutlineAccessKey(serverId, accessKeyName);
        outlineOrderAccessKeyEntity.setId(outlineAccessKeyEntity.getId());

        outlineOrderAccessKeyService.save(outlineOrderAccessKeyEntity);

        orderService.ship(order.getId());
    }

    private long[] calculateOutlineOrderStartAndExpireTime(String days) {
        LocalDateTime now = LocalDateTime.now();
        long nowTime = now.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();

        LocalDateTime expire;
        if (StringUtils.isNumeric(days)) {
            expire = now.plusDays(Converter.parseInt(days));
        } else {
            if (days.endsWith("m")) {
                int months = Converter.parseInt(days.substring(0, days.length()-1));
                expire = now.plusMonths(months);
            } else if (days.endsWith("y")) {
                int years = Converter.parseInt(days.substring(0, days.length()-1));
                expire = now.plusYears(years);
            } else {
                throw new ErrorCodeException(ErrorCode.of("outline.order.days.invalid", days));
            }
        }

        expire = expire.toLocalDate().plusDays(1).atStartOfDay(ZoneOffset.ofHours(8)).toLocalDateTime();
        long expireTime = expire.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();

        return new long[] {nowTime, expireTime};
    }

    public void createOutlineOrder(OutlineOrderCreateParam param) {
        Order order = orderService.findOrder(param.getOrderId());
        if (null == order) {
            throw new ErrorCodeException(ErrorCode.of("order.id.invalid", param.getOrderId()));
        }

        String serverId = param.getServerId();

        createOutlineOrder(order, serverId);
    }

    public static void main(String[] args) {
        OutlineOrderService outlineOrderService = new OutlineOrderService();
        System.out.println(Arrays.asList(outlineOrderService.calculateOutlineOrderStartAndExpireTime("1m")));
        System.out.println(Arrays.asList(outlineOrderService.calculateOutlineOrderStartAndExpireTime("1y")));
        System.out.println(Arrays.asList(outlineOrderService.calculateOutlineOrderStartAndExpireTime("30")));

    }
}
