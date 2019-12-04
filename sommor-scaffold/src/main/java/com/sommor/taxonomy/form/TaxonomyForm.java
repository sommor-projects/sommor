package com.sommor.taxonomy.form;

import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.taxonomy.model.TaxonomyRelationConfig;
import com.sommor.view.FieldView;
import com.sommor.view.FormView;
import com.sommor.view.config.Fieldset;
import com.sommor.view.config.HiddenInput;
import com.sommor.view.config.TextInput;
import com.sommor.view.form.EntityForm;
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
public class TaxonomyForm extends EntityForm<TaxonomyEntity> {

    @HiddenInput
    private Integer id;

    @TextInput
    @NotBlank
    private String subTitle;

    @TextInput
    private String slug;

    @TextInput
    @NotBlank
    private String title;

    @TaxonomySelect(tree = true, rootSelection = true)
    @NotNull
    private Integer parentId;

    @HiddenInput
    @NotNull
    private Integer typeId;

    @Fieldset(TaxonomyRelationConfig.class)
    private List<TaxonomyRelationConfig> relationConfigs;

    @Override
    public void fromEntity(TaxonomyEntity taxonomyEntity) {
        super.fromEntity(taxonomyEntity);

        if (null != taxonomyEntity.getConfig()) {
            this.relationConfigs = taxonomyEntity.getConfig().getList(TaxonomyRelationConfig.class);
        }
    }

    @Override
    public void renderField(FieldView fieldView) {
        super.renderField(fieldView);

        if (fieldView instanceof TaxonomySelectView && "parentId".equals(fieldView.getFullName())) {
            TaxonomySelectView selectView = (TaxonomySelectView) fieldView;
            selectView.setParentId(this.getParentId());
            selectView.setTypeId(this.getTypeId());
        }
    }
}
