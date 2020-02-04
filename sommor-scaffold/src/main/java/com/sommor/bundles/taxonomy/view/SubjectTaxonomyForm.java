package com.sommor.bundles.taxonomy.view;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.view.fields.taxonomy.select.TaxonomySelect;
import com.sommor.bundles.taxonomy.view.fields.taxonomy.select.TaxonomySelectView;
import com.sommor.core.view.field.FieldRenderContext;
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
    @TaxonomySelect(tree = true)
    @NotNull
    private Integer taxonomy;

    private TaxonomyEntity typeEntity;

    public SubjectTaxonomyForm(TaxonomyEntity typeEntity) {
        this.typeEntity = typeEntity;
    }

    public void renderTaxonomy(FieldRenderContext ctx) {
        TaxonomySelectView view = ctx.getFieldView();
        view.setTypeId(typeEntity.getId());
        view.setTitle(typeEntity.getTitle() + "类型");
    }
}
