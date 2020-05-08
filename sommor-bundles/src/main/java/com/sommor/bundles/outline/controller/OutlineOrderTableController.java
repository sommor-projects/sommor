package com.sommor.bundles.outline.controller;

import com.sommor.bundles.outline.entity.OutlineOrderEntity;
import com.sommor.bundles.outline.model.OutlineOrderTable;
import com.sommor.core.component.table.TableController;
import com.sommor.core.scaffold.param.EntityQueryParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
@RestController
@RequestMapping(value = "/api/outline/order")
public class OutlineOrderTableController extends TableController<OutlineOrderEntity, OutlineOrderTable, EntityQueryParam> {
}
