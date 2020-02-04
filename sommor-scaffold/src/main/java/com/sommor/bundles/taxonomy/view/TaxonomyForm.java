package com.sommor.bundles.taxonomy.view;

import com.sommor.bundles.taxonomy.view.fields.subject.style.SubjectTaxonomyStyleField;
import com.sommor.bundles.taxonomy.view.fields.taxonomy.select.TaxonomySelect;
import com.sommor.bundles.taxonomy.view.fields.taxonomy.select.TaxonomySelectView;
import com.sommor.bundles.taxonomy.model.TaxonomyRelationConfig;
import com.sommor.core.view.FieldView;
import com.sommor.core.view.field.FieldRenderContext;
import com.sommor.mybatis.sql.field.type.ConfigKey;
import com.sommor.core.view.field.EntityForm;
import com.sommor.core.view.FormFieldView;
import com.sommor.core.view.field.config.FieldsetConfig;
import com.sommor.core.view.field.config.TextField;
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

    @TextField(title = "名称")
    private String name;

    @TextField(title = "分组名")
    private String group;

    @FieldsetConfig(TaxonomyRelationConfig.class)
    private List<TaxonomyRelationConfig> relationConfigs;

    @SubjectTaxonomyStyleField(title = "字段样式")
    @ConfigKey("fs")
    private String fieldStyle;

    public void renderParentId(FieldRenderContext ctx) {
        TaxonomySelectView selectView = ctx.getFieldView();
        selectView.setParentId(this.getParentId());

        if (this.getParentId() == null || this.getParentId() == 0) {
            selectView.setRootSelection(true);
        }
    }
}
