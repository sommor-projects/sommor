package com.sommor.bundles.taxonomy.view;

import com.sommor.bundles.taxonomy.fields.subject.style.SubjectTaxonomyStyleField;
import com.sommor.bundles.taxonomy.fields.taxonomy.select.TaxonomySelect;
import com.sommor.bundles.taxonomy.fields.taxonomy.select.TaxonomySelectView;
import com.sommor.bundles.taxonomy.model.TaxonomyRelationConfig;
import com.sommor.mybatis.sql.field.type.ConfigKey;
import com.sommor.scaffold.view.field.EntityForm;
import com.sommor.scaffold.view.FieldView;
import com.sommor.scaffold.view.field.config.FieldsetConfig;
import com.sommor.scaffold.view.field.config.TextField;
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
public class TaxonomyForm extends EntityForm {

    @TaxonomySelect(tree = true, selfSelection = true, title = "父分类")
    @NotNull
    private Integer parentId;

    @TextField(title = "标题")
    @NotBlank
    private String title;

    @TextField(title = "副标题")
    @NotBlank
    private String subTitle;

    @TextField(title = "Slug")
    private String slug;

    @FieldsetConfig(TaxonomyRelationConfig.class)
    private List<TaxonomyRelationConfig> relationConfigs;

    @SubjectTaxonomyStyleField(title = "字段样式")
    @ConfigKey("fs")
    private String fieldStyle;

    @Override
    public void onFieldRender(FieldView fieldView) {
        if (fieldView instanceof TaxonomySelectView && "parentId".equals(fieldView.getFullName())) {
            TaxonomySelectView selectView = (TaxonomySelectView) fieldView;
            selectView.setParentId(this.getParentId());

            if (this.getParentId() == null || this.getParentId() == 0) {
                selectView.setRootSelection(true);
            }
        }
    }
}
