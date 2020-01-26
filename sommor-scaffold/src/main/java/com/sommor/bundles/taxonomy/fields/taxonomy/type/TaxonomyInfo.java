package com.sommor.bundles.taxonomy.fields.taxonomy.type;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/25
 */
public class TaxonomyInfo {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String slug;

    public TaxonomyInfo() {
    }

    public TaxonomyInfo(TaxonomyEntity entity) {
        this.setId(entity.getId());
        this.setTitle(entity.getTitle());
        this.setSlug(entity.getSlug());
    }
}
