package com.sommor.menu.model;

import com.sommor.view.FormView;
import com.sommor.view.config.HiddenInput;
import com.sommor.view.config.TextInput;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-10
 */
@Getter
@Setter
public class MenuForm extends FormView {

    @HiddenInput
    private Integer id;

    @TextInput
    @NotBlank
    private String title;

    @TextInput
    @NotBlank
    private String slug;
}
