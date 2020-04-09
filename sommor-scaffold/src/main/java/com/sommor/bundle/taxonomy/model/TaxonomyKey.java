package com.sommor.bundle.taxonomy.model;

import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/4
 */
public class TaxonomyKey {

    @Getter
    private String type;

    @Getter
    private String name;

    @Getter
    private String key;

    public TaxonomyKey(String type, String name) {
        this.type = type;
        this.name = name;
        this.key = type + ":" + name;
    }

    public String getTaxonomyType() {
        return isRoot() ? this.getName() : this.getType();
    }

    public boolean isRoot() {
        return TaxonomyEntity.ROOT.equals(type);
    }

    public static TaxonomyKey of(String key) {
        String type;
        String name;

        int i = key.indexOf(":");
        if (i > 0) {
            type = key.substring(0, i);
            name = key.substring(i + 1);
        } else {
            name = key;
            type = TaxonomyEntity.ROOT;
        }

        return new TaxonomyKey(type, name);
    }

    public static TaxonomyKey of(TaxonomyEntity entity) {
        return new TaxonomyKey(entity.getType(), entity.getName());
    }
}
