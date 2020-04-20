package com.sommor.bundles.outline.entity;

import com.sommor.core.scaffold.entity.timed.TimedEntity;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/16
 */
@Getter
@Setter
@Table(value = "outline_servers", entityName = "outlineServer")
public class OutlineServerEntity extends TimedEntity<String> {

}
