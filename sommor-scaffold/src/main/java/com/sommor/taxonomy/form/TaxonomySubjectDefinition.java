package com.sommor.taxonomy.form;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/1
 */
@Getter
public class TaxonomySubjectDefinition {

    private String tableName;

    private String title;

    public TaxonomySubjectDefinition(String tableName, String title) {
        this.tableName = tableName;
        this.title = title;
    }
}
