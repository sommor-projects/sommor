package com.sommor.taxonomy.entity;

import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Table("item_attribute_options")
@Getter
@Setter
public class ItemAttributeOptionEntity {

    private Integer id;

    private Integer itemId;

    private Integer attributeId;

    private Integer optionId;
}
