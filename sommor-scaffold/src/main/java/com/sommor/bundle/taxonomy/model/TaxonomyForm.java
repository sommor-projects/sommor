package com.sommor.bundle.taxonomy.model;

import com.sommor.bundle.taxonomy.component.relation.TaxonomyRelationSetting;
import com.sommor.bundle.taxonomy.component.select.TaxonomySelectFieldConfig;
import com.sommor.bundle.taxonomy.component.select.TaxonomySelectField;
import com.sommor.bundle.taxonomy.component.style.TaxonomyStyleField;
import com.sommor.component.form.EntityForm;
import com.sommor.component.form.field.InputField;
import com.sommor.model.Model;
import com.sommor.model.define.ModelAware;
import com.sommor.mybatis.sql.field.type.ConfigKey;
import com.sommor.view.context.ViewRenderContext;
import com.sommor.view.model.OnViewRender;
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
public class TaxonomyForm extends EntityForm implements OnViewRender {

    @TaxonomySelectField(tree = true, includeSelf = true, title = "父分类")
    @NotNull
    private Integer parentId;

    @InputField(title = "标题")
    @NotBlank
    private String title;

    @InputField(title = "副标题")
    @NotBlank
    private String subTitle;

    @InputField(title = "名称")
    private String name;

    @InputField(title = "分组名")
    private String group;

    @ModelAware(TaxonomyRelationSetting.class)
    private List<TaxonomyRelationSetting> relationConfigs;

    @TaxonomyStyleField(title = "字段样式")
    @ConfigKey("fs")
    private String fieldStyle;

    @Override
    public void onViewRender(Model model, ViewRenderContext ctx) {
        TaxonomySelectFieldConfig config = model.getField("parentId").getFieldConfig();
        config.setParentId(this.getParentId());
        if (this.getParentId() == null || this.getParentId() == 0) {
            config.setIncludeRoot(true);
        }
    }
}
