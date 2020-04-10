package com.sommor.bundles.taxonomy.model;

import com.sommor.bundles.taxonomy.component.relation.TaxonomyAttributeSetting;
import com.sommor.bundles.taxonomy.component.select.TaxonomySelectFieldConfig;
import com.sommor.bundles.taxonomy.component.select.TaxonomySelectField;
import com.sommor.bundles.taxonomy.component.style.TaxonomyStyleField;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.core.component.form.EntityForm;
import com.sommor.core.component.form.field.InputField;
import com.sommor.core.model.Model;
import com.sommor.core.model.define.ModelAware;
import com.sommor.mybatis.sql.field.type.ConfigKey;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.model.OnViewRender;
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
    private String parent;

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

    @ModelAware(TaxonomyAttributeSetting.class)
    private List<TaxonomyAttributeSetting> attributeSettings;

    @TaxonomyStyleField(title = "字段样式")
    @ConfigKey("fs")
    private String fieldStyle;

    @Override
    public void onViewRender(Model model, ViewRenderContext ctx) {
        TaxonomySelectFieldConfig config = model.getField("parent").getFieldConfig();

        Object target = ctx.getSourceMode().getTarget();
        if (target instanceof TaxonomyFormParam) {
            String parent = ((TaxonomyFormParam) target).getParent();
            TaxonomyKey key = TaxonomyKey.of(parent);
            config.setType(key.getTaxonomyType());
        } else if (target instanceof TaxonomyEntity) {
            TaxonomyKey key = TaxonomyKey.of((TaxonomyEntity) target);
            config.setType(key.getTaxonomyType());
        } else {
            config.setIncludeRoot(true);
        }
    }
}
