package com.sommor.bundles.taxonomy.model;

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
    private String key;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String name;

    public TaxonomyInfo() {
    }

    public TaxonomyInfo(TaxonomyEntity entity) {
        this.setId(entity.getId());
        this.setKey(entity.getKey());
        this.setTitle(entity.getTitle());
        this.setName(entity.getName());
    }
}
