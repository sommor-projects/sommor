package com.sommor.bundles.mall.view;

import com.sommor.scaffold.view.field.EntityTable;
import com.sommor.scaffold.view.field.config.TextField;
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
}
