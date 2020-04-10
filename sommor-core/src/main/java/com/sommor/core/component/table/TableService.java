package com.sommor.core.component.table;

import com.sommor.core.utils.ClassAnnotatedTypeParser;
import com.sommor.core.curd.BaseCurdService;
import com.sommor.core.model.Model;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.core.view.ViewEngine;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
public class TableService<Entity extends BaseEntity, Table, QueryParam> extends BaseCurdService<Entity> {

    private Class<Table> tableClass;

    public TableService(Class<Entity> entityClass, Class<Table> tableClass) {
        super(entityClass);
        this.tableClass = tableClass;
    }

    public TableService() {
        super();
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.tableClass = classes[1];
    }

    public TableView renderEntityTable(QueryParam queryParam) {
        Model queryModel = Model.of(queryParam);
        PagingResult<Entity> pagingResult = curdService().queryByPaging(queryModel);

        Object target;
        try {
            target = this.tableClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        Model tableModel = Model.of(target);


        TableViewConfig config = new TableViewConfig();
        config.setTableModel(tableModel);
        config.setSearchModel(queryModel);
        config.setPagingResult(pagingResult);

        return ViewEngine.render(config);
    }

}
