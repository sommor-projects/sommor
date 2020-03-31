package com.sommor.component.entity.select;

import com.sommor.component.form.field.Option;
import com.sommor.extensibility.config.Extension;
import com.sommor.mybatis.entity.BaseEntity;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/2
 */
@Extension(name = "解析实体搜索时的选项")
public interface EntitySelectOptionParser<Entity extends BaseEntity> {

    Option parseEntitySelectOption(Entity entity);

}
