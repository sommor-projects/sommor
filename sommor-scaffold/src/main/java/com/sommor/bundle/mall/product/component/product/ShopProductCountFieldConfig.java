package com.sommor.bundle.mall.product.component.product;

import com.sommor.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/7
 */
public class ShopProductCountFieldConfig extends TargetConfig {

     @Getter
     @Setter
     private Integer shopId;

}
