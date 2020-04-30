package com.sommor.bundles.taxonomy.component.attribute;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 在表单中，主体内容选择的分类
 *
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
public class AttributeSelection {

    /**
     * 主体内容表中taxonomy字段对应的值
     */
    @Getter
    @Setter
    private String taxonomy;

    @Getter
    @Setter
    private LinkedHashMap<String, Object> attributes;

    public void addAttribute(String type, Object selections) {
        if (null == attributes) {
            attributes = new LinkedHashMap<>();
        }
        attributes.put(type, selections);
    }

}
