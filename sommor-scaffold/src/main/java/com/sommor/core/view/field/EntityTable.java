package com.sommor.core.view.field;

import com.sommor.core.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/25
 */
public class EntityTable implements OnFill, OnTableRowFill {

    @Getter
    @Setter
    @TextField(title = "ID")
    private Integer id;

    @Override
    public void onFill(DataSource targetData) {
    }

    @Override
    public void onTableRowFill(int row, int total, DataSource dataSource) {
    }
}
