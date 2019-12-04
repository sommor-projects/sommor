package com.sommor.taxonomy.form;


import com.alibaba.fastjson.JSON;
import com.sommor.taxonomy.model.TaxonomyRelationConfig;
import com.sommor.view.config.Fieldset;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/3
 */
@Fieldset(processFieldType = true)
public class SubjectTaxonomySelectionField extends HashMap<String, Object> {

    public SubjectTaxonomySelectionField() {
        super();
    }

    public SubjectTaxonomySelectionField(Map<? extends String, ?> m) {
        super(m);
    }
}
