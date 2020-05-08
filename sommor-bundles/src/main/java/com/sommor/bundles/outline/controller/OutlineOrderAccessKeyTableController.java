package com.sommor.bundles.outline.controller;

import com.sommor.bundles.outline.entity.OutlineOrderAccessKeyEntity;
import com.sommor.bundles.outline.model.OutlineOrderAccessKeyQueryParam;
import com.sommor.bundles.outline.model.OutlineOrderAccessKeyTable;
import com.sommor.core.component.table.TableController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
@RestController
@RequestMapping(value = "/api/outline/order/accesskey")
public class OutlineOrderAccessKeyTableController extends TableController<OutlineOrderAccessKeyEntity, OutlineOrderAccessKeyTable, OutlineOrderAccessKeyQueryParam> {
}
