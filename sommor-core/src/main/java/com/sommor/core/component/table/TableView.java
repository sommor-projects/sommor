package com.sommor.core.component.table;

import com.sommor.core.component.form.FormView;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.core.view.View;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
public class TableView extends View {

    @Getter
    @Setter
    private FormView searchForm;

    @Getter
    @Setter
    private PagingResult<Map<String, Object>> data;

    public TableView() {
        super("table");
    }
}
