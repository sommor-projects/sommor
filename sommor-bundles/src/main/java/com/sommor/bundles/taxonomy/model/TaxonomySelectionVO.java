package com.sommor.bundles.taxonomy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
@Getter
@Setter
public class TaxonomySelectionVO {

    private TaxonomyInfoVO type;

    private List<TaxonomyInfoVO> items;

}
