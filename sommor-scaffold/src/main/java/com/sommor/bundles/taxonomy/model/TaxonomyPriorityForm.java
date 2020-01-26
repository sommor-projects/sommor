package com.sommor.bundles.taxonomy.model;

import com.sommor.scaffold.view.field.EntityForm;
import com.sommor.scaffold.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Getter
@Setter
public class TaxonomyPriorityForm extends EntityForm {

    @TextField
    @NotBlank
    private String direction;
}
