package com.sommor.core.view;

import com.sommor.mybatis.query.PagingResult;
import com.sommor.core.view.field.DataSource;
import com.sommor.core.view.field.OnTableRowFill;
import com.sommor.core.view.field.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
public class TableView extends FieldsetView {

    @Getter
    @Setter
    private FormView searchForm;

    private Table table;

    private PagingResult<Map<String, Object>> tableData;

    public TableView(Table table) {
        super("table", table.getTargetData());

        this.table = table;
        this.setName(table.getTargetClass().getSimpleName());
    }

    public Map<String, Object> getFields() {
        return this.toFields();
    }

    public PagingResult<Map<String, Object>> getData() {
        return this.tableData;
    }

    public void render(PagingResult pagingResult) {
        List<Map<String, Object>> tableData = new ArrayList<>();

        Table table = this.table;
        this.renderFieldsetView(this);

        OnTableRowFill onTableRowFill = null;
        if (table.getTargetData().getTarget() instanceof OnTableRowFill) {
            onTableRowFill = (OnTableRowFill) table.getTargetData().getTarget();
        }

        int row = 0;
        int total = pagingResult.getData().size();
        for (Object entity : pagingResult.getData()) {
            DataSource sourceData = new DataSource(entity);
            table.reset();
            table.fill(sourceData);
            if (null != onTableRowFill) {
                onTableRowFill.onTableRowFill(row, total, sourceData);
            }
            tableData.add(table.toData());
        }

        this.tableData = PagingResult.of(pagingResult, tableData);
    }
}
