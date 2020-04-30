package com.sommor.bundles.outline.model;

import com.sommor.bundles.outline.entity.OutlineAccessKeyEntity;
import com.sommor.core.component.entity.count.EntityCountField;
import com.sommor.core.component.table.EntityTable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@Getter
@Setter
public class OutlineServerTable extends EntityTable {

    private String name;

    @EntityCountField(foreignEntity = OutlineAccessKeyEntity.NAME, foreignIdName = "outlineServerId")
    private Integer accessKeyCount;

}
