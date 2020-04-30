package com.sommor.bundles.mall.order.model;

import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.core.component.entity.select.EntitySelectField;
import com.sommor.core.component.form.EntityForm;
import com.sommor.core.component.form.field.SwitchField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Getter
@Setter
public class AdminOrderForm extends OrderForm {

    @NotNull
    @EntitySelectField(entityName = UserEntity.NAME, title = "买家")
    private Long buyerId;

    @SwitchField(title = "是否已付款")
    private Boolean paid;
}
