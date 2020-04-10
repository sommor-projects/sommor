package com.sommor.bundles.taxonomy.component.relation;

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
public class TaxonomyAttributeSelection {

    /**
     * 主体内容表中taxonomy字段对应的值
     */
    @Getter
    @Setter
    private String taxonomy;

    @Getter
    @Setter
    private Map<String, Object> attributes;

    public void addAttribute(String type, Object selections) {
        if (null == attributes) {
            attributes = new HashMap<>();
        }
        attributes.put(type, selections);
    }

}
