package com.sommor.bundles.outline.controller;

import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.bundles.outline.model.OutlineAccessKeyQueryParam;
import com.sommor.bundles.outline.model.OutlineAccessKeyTable;
import com.sommor.core.component.table.TableController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@RestController
@RequestMapping(value = "/api/outline-accesskey")
public class OutlineAccessKeyTableController extends TableController<OutlineAccessKeyEntity, OutlineAccessKeyTable, OutlineAccessKeyQueryParam> {
}
