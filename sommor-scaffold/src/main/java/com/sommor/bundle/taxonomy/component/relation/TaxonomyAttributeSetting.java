package com.sommor.bundle.taxonomy.component.relation;

import com.sommor.bundle.taxonomy.component.select.TaxonomySelectField;
import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.component.form.field.SwitchField;
import com.sommor.component.entity.name.EntityNameSelectField;
import com.sommor.mybatis.sql.field.type.ConfigKey;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/26
 */
@Getter
@Setter
@ConfigKey("trc")
public class TaxonomyAttributeSetting {

    @NotBlank
    @EntityNameSelectField(title = "实体")
    private String subject;

    @NotNull
    @TaxonomySelectField(type = TaxonomyEntity.ROOT,  title = "属性")
    private String type;

    @SwitchField(title = "多选")
    private Boolean multiple;

    @SwitchField(title = "必选")
    private Boolean required;
}
