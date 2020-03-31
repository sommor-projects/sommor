package com.sommor.bundle.taxonomy.component.relation;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 在表单中，主体内容选择的分类
 *
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
public class TaxonomyRelationSelection {

    /**
     * 主体内容表中taxonomy字段对应的值
     */
    @Getter
    @Setter
    private Integer taxonomyId;

    @Getter
    @Setter
    private Map<String, Object> relations;

    public void addRelation(String type, Object selections) {
        if (null == relations) {
            relations = new HashMap<>();
        }
        relations.put(type, selections);
    }

}
