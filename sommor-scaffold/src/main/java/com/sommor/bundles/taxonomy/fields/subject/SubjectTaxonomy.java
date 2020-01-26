package com.sommor.bundles.taxonomy.fields.subject;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
@SubjectTaxonomyField
public class SubjectTaxonomy {

    @Getter @Setter
    private Integer typeId;

    @Getter @Setter
    private Map<String, Object> relations;

    public void addRelation(String type, Object selections) {
        if (null == relations) {
            relations = new HashMap<>();
        }
        relations.put(type, selections);
    }

}
