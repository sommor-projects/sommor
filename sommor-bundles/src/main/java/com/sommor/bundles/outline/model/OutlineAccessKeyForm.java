package com.sommor.bundles.outline.model;

import com.sommor.core.component.form.field.InputField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@Getter
@Setter
public class OutlineAccessKeyForm {

    private String id;

    @NotBlank
    private String outlineServerId;

    @NotBlank
    @InputField(title = "名称")
    private String name;
}
