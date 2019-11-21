package com.sommor.taxonomy.model;

import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.taxonomy.form.TaxonomySelect;
import com.sommor.view.FormView;
import com.sommor.view.config.HiddenInput;
import com.sommor.view.config.TextInput;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Getter
@Setter
public class TaxonomyForm extends FormView<TaxonomyEntity> {

    @HiddenInput
    private Integer id;

    @TextInput
    @NotBlank
    private String name;

    @TextInput
    @NotBlank
    private String title;

    @TaxonomySelect
    @NotNull
    private Integer parentId;

    @HiddenInput
    @NotNull
    private Integer rootId;

    private List<Integer> relatedTaxonomies;

    private List<Integer> relatedAttributes;
}
