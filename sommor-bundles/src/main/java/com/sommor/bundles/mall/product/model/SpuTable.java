package com.sommor.bundles.mall.product.model;

import com.sommor.core.component.table.EntityTable;
import com.sommor.core.view.field.text.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/22
 */
public class SpuTable extends EntityTable {

    @Getter
    @Setter
    @TextField(title = "标题")
    private String title;

    @Getter
    @Setter
    @TextField(title = "标题")
    private String subTitle;
}
