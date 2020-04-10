package com.sommor.core.component.table;

import com.sommor.core.api.response.ApiResponse;
import com.sommor.core.utils.ClassAnnotatedTypeParser;
import com.sommor.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
public class TableController<Entity extends BaseEntity, Table, QueryParam> {

    private TableService<Entity, Table, QueryParam> tableService;

    public TableController() {
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.tableService = new TableService<>(classes[0], classes[1]);
    }

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }


    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<TableView> renderTable(QueryParam queryParam) {
        TableView tableView = tableService.renderEntityTable(queryParam);
        return ApiResponse.success(tableView);
    }
}
