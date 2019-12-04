package com.sommor.taxonomy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/24
 */
public class TaxonomyDetail {

    @Getter
    @Setter
    private List<TaxonomyItem> paths;

    public TaxonomyItem getTaxonomy() {
        if (null == this.paths) {
            return null;
        }
        return this.paths.get(this.paths.size()-1);
    }

    public TaxonomyItem getRootTaxonomy() {
        if (null == this.paths) {
            return null;
        }
        return this.paths.get(0);
    }
}
