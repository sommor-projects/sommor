package com.sommor.bundle.wine.model;

import com.sommor.bundle.mall.product.entity.ProductEntity;
import com.sommor.bundle.media.component.file.MediaFilesField;
import com.sommor.bundle.wine.component.info.WineInfoField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
@Getter
@Setter
@WineInfoField
public class WineInfoVO {

    private Integer productId;

    private String title;

    private String subTitle;

    @MediaFilesField(entity = ProductEntity.NAME, entityIdFieldName = "productId", coverFieldName = "cover")
    private String coverUrl;
}
