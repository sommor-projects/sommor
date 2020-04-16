package com.sommor.bundles.taxonomy.model;

import com.sommor.core.model.EntityForm;
import com.sommor.core.view.field.text.TextField;
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
