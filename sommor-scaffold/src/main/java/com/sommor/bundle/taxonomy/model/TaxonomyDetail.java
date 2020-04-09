package com.sommor.bundle.taxonomy.model;

import com.sommor.bundle.taxonomy.component.key.TaxonomyKeyField;
import com.sommor.bundle.taxonomy.component.path.TaxonomyPathField;
import com.sommor.bundle.taxonomy.component.foreign.ForeignTaxonomyField;
import com.sommor.model.target.EntityDetail;
import com.sommor.view.field.text.TextField;
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
    @TaxonomyKeyField
    private String key;

    @Getter
    @Setter
    @TextField
    private String name;

    @Getter
    @Setter
    @TextField
    private String title;

    @Getter
    @Setter
    @ForeignTaxonomyField(taxonomyFieldName = "type", typeFieldName = "type")
    private TaxonomyInfo type;

    @Getter
    @Setter
    @TaxonomyPathField(taxonomyFieldName = "name", typeFieldName = "type")
    private List<TaxonomyInfo> path;

}
