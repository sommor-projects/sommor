package com.sommor.bundles.outline.model;

import com.sommor.bundles.outline.entity.OutlineServerEntity;
import com.sommor.bundles.taxonomy.component.attribute.AttributeSelection;
import com.sommor.bundles.taxonomy.component.attribute.AttributeSelectionField;
import com.sommor.core.component.datetime.DateTimeRangeField;
import com.sommor.core.component.entity.select.EntitySelectField;
import com.sommor.core.component.form.field.InputField;
import com.sommor.core.utils.DateTimeUtil;
import com.sommor.mybatis.entity.config.Column;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@Getter
@Setter
public class OutlineServerForm {

    private String id;

    @NotBlank
    @InputField(title = "服务器名")
    private String name;

    @NotBlank
    @InputField(title = "apiUrl")
    private String apiUrl;

    @NotNull
    @DateTimeRangeField(title = "起止日期")
    private String[] startEndTime;

    @AttributeSelectionField(entityName = OutlineServerEntity.NAME, taxonomy = OutlineServerEntity.NAME)
    private AttributeSelection taxonomy;

    @EntitySelectField(title = "代理", entityName = OutlineServerEntity.NAME)
    private Long agencyUserId;
}
