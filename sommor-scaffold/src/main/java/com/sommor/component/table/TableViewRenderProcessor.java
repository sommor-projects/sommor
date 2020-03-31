package com.sommor.component.table;

import com.sommor.component.form.FormView;
import com.sommor.component.form.FormViewConfig;
import com.sommor.component.form.action.Search;
import com.sommor.extensibility.config.Implement;
import com.sommor.model.Model;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.view.ViewEngine;
import com.sommor.view.context.ViewRenderContext;
import com.sommor.view.extension.ViewRenderProcessor;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
@Implement
public class TableViewRenderProcessor implements ViewRenderProcessor<TableViewConfig> {

    @Override
    public void processOnViewRender(TableViewConfig tableViewConfig, ViewRenderContext ctx) {
        Model tableModel = tableViewConfig.getTableModel();

        Object tableTarget = tableViewConfig.getTableModel().getTarget();

        OnTableRowFill onTableRowFill = null;
        if (tableTarget instanceof OnTableRowFill) {
            onTableRowFill = (OnTableRowFill) tableTarget;
        }

        List<Map<String, Object>> tableData = new ArrayList<>();

        PagingResult<?> pagingResult = tableViewConfig.getPagingResult();
        List<?> entities = pagingResult.getData();
        if (CollectionUtils.isNotEmpty(entities)) {
            List<Model> models = entities.stream().map(p -> Model.of(p)).collect(Collectors.toList());
            int total = entities.size();
            int row = 0;
            for (Model sourceModel : models) {
                tableModel.reset();
                tableModel.fill(sourceModel);
                if (null != onTableRowFill) {
                    onTableRowFill.onTableRowFill(row, total, sourceModel);
                }
                tableData.add(tableModel.toMap());
                row++;
            }
        }

        TableView tableView = ctx.getView();
        tableView.setName(tableTarget.getClass().getSimpleName());
        tableView.setData(PagingResult.of(pagingResult, tableData));

        Model searchModel = tableViewConfig.getSearchModel();
        if (null != searchModel) {
            FormViewConfig fvc = new FormViewConfig();
            fvc.setModel(searchModel);
            fvc.setFormAction(Search.ACTION);

            FormView formView = ViewEngine.render(fvc);
            tableView.setSearchForm(formView);
        }
    }
}
