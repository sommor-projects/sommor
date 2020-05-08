package com.sommor.core.component.status;

import com.sommor.core.component.form.field.SelectView;
import com.sommor.core.view.field.FieldConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
@Getter
@Setter
public class StatusFieldConfig extends FieldConfig<SelectView> {

    private Integer status;

    private String statusFieldName;

}
