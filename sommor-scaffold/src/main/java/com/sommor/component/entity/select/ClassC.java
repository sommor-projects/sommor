package com.sommor.component.entity.select;

import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.mybatis.entity.BaseEntity;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/28
 */
public class ClassC<B, A extends BaseEntity> implements InterfaceA<A, ProductEntity>, InterfaceB<B> {
}
