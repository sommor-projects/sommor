package com.sommor.bundle.taxonomy.model;

import com.sommor.bundle.taxonomy.component.select.TaxonomySelectField;
import com.sommor.bundle.taxonomy.component.select.TaxonomySelectFieldConfig;
import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.view.model.ModelFieldViewRenderContext;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class SubjectTaxonomyForm {

    @Getter
    @Setter
    @TaxonomySelectField(tree = true)
    @NotNull
    private String taxonomy;

    private TaxonomyEntity typeEntity;

    public SubjectTaxonomyForm(TaxonomyEntity typeEntity) {
        this.typeEntity = typeEntity;
    }

    public void renderTaxonomy(ModelFieldViewRenderContext ctx) {
        TaxonomySelectFieldConfig config = ctx.getFieldConfig();
        config.setType(typeEntity.getName());
        config.setTitle(typeEntity.getTitle() + "类型");
    }
}
