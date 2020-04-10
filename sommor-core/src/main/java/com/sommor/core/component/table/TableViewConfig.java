package com.sommor.core.component.table;

import com.sommor.core.model.Model;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.core.view.ViewConfig;
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
