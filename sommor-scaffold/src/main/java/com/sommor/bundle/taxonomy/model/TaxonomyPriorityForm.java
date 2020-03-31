package com.sommor.bundle.taxonomy.model;

import com.sommor.model.target.EntityForm;
import com.sommor.view.field.text.TextField;
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
