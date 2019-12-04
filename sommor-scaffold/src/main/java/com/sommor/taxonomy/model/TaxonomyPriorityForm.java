package com.sommor.taxonomy.model;

import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.view.config.HiddenInput;
import com.sommor.view.config.TextInput;
import com.sommor.view.form.EntityForm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Getter
@Setter
public class TaxonomyPriorityForm extends EntityForm<TaxonomyEntity> {

    @HiddenInput
    @NotNull
    private Integer id;

    @TextInput
    @NotBlank
    private String direction;
}
