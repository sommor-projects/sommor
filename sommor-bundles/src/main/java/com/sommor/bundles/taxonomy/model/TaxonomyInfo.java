package com.sommor.bundles.taxonomy.model;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.core.utils.Converter;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/25
 */
public class TaxonomyInfo {

    @Getter
    @Setter
    private String id;

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
        this.setId(Converter.toString(entity.getId()));
        this.setKey(entity.getKey());
        this.setTitle(entity.getTitle());
        this.setName(entity.getName());
    }
}
