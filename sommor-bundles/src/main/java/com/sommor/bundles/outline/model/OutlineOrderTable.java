package com.sommor.bundles.outline.model;

import com.sommor.bundles.mall.order.model.Buyer;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.core.component.datetime.DateTimeField;
import com.sommor.core.model.enricher.EntityReference;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
@Getter
@Setter
public class OutlineOrderTable {

    private Long id;

    @EntityReference(entity = UserEntity.NAME, byField = "buyerId")
    private Buyer buyer;

    @DateTimeField
    private String startTime;

    @DateTimeField
    private String endTime;

}
