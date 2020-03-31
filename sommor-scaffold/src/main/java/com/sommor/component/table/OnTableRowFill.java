package com.sommor.component.table;

import com.sommor.model.Model;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/26
 */
public interface OnTableRowFill {

    void onTableRowFill(int row, int total, Model model);

}
