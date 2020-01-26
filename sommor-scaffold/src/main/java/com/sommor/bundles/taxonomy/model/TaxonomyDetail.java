package com.sommor.bundles.taxonomy.model;

import com.sommor.bundles.taxonomy.fields.taxonomy.paths.TaxonomyPathField;
import com.sommor.bundles.taxonomy.fields.taxonomy.type.TaxonomyInfo;
import com.sommor.bundles.taxonomy.fields.taxonomy.type.TaxonomyTypeField;
import com.sommor.scaffold.view.field.EntityDetail;
import com.sommor.scaffold.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/24
 */
public class TaxonomyDetail extends EntityDetail {

    @Getter
    @Setter
    @TextField
    private String slug;

    @Getter
    @Setter
    @TextField
    private String title;

    @Getter
    @Setter
    @TaxonomyTypeField(name = "typeId")
    private TaxonomyInfo type;

    @Getter
    @Setter
    @TaxonomyPathField(name = "id")
    private List<TaxonomyInfo> path;

}