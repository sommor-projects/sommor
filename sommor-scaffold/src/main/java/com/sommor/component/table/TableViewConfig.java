package com.sommor.component.table;

import com.sommor.model.Model;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.view.ViewConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
public class TableViewConfig extends ViewConfig<TableView> {

    @Getter
    @Setter
    private Model tableModel;

    @Getter
    @Setter
    private Model searchModel;

    @Getter
    @Setter
    private PagingResult pagingResult;
}
