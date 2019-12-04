package com.sommor.taxonomy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/26
 */
public class TaxonomyConfig {

    @Getter
    @Setter
    private List<TaxonomyRelationConfig> relatedTaxonomies;
}
