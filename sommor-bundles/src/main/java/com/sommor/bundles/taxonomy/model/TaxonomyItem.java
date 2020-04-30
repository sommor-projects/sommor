package com.sommor.bundles.taxonomy.model;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/24
 */
public class TaxonomyItem implements Comparable<TaxonomyItem> {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String key;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String subTitle;

    @Getter
    @Setter
    private Integer priority;

    @Getter
    @Setter
    private Boolean highestPriority;

    @Getter
    @Setter
    private Boolean lowestPriority;

    @Getter
    @Setter
    private Integer subTaxonomyCount;

    public void from(TaxonomyEntity entity) {
        this.setName(entity.getName());
        this.setKey(entity.getKey());
        this.setTitle(entity.getTitle());
        this.setSubTitle(entity.getSubTitle());
        this.setPriority(entity.getPriority());
    }

    @Override
    public int compareTo(TaxonomyItem o) {
        return this.getPriority() - o.getPriority();
    }
}
