package com.sommor.core.component.table;

import com.sommor.core.model.Model;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/26
 */
public interface OnTableRowFill {

    void onTableRowFill(int row, int total, Model model);

}
