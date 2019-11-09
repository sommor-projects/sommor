package com.sommor.taxonomy.model;

import com.sommor.view.FormView;
import com.sommor.view.config.HiddenInput;
import com.sommor.view.config.TextInput;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Getter
@Setter
public class AttributeForm extends FormView {

    @HiddenInput
    private Integer id;

    @TextInput
    @NotBlank
    private String name;

    @TextInput
    @NotBlank
    private String title;
}
