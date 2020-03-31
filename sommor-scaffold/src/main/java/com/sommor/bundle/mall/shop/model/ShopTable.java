package com.sommor.bundle.mall.shop.model;

import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.bundle.mall.product.component.product.ShopProductCountField;
import com.sommor.bundle.media.component.file.MediaFilesField;
import com.sommor.component.table.EntityTable;
import com.sommor.view.field.text.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
public class ShopTable extends EntityTable {

    @Getter
    @Setter
    @TextField(title = "名称")
    private String title;

    @Getter
    @Setter
    @TextField(title = "名称")
    private String subTitle;

    @Getter
    @Setter
    @MediaFilesField(entity = ShopEntity.NAME, title = "Logo")
    private String logo;

    @Getter
    @Setter
    @ShopProductCountField(title = "商品数")
    private Integer productCount;
}
