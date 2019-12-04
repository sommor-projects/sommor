package com.sommor.taxonomy.model;

import com.sommor.taxonomy.entity.TaxonomyEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/24
 */
public class TaxonomyItem implements Comparable<TaxonomyItem> {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String slug;

    @Getter
    @Setter
    private String title;

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
        this.setId(entity.getId());
        this.setName(entity.getSubTitle());
        this.setSlug(entity.getSlug());
        this.setTitle(entity.getTitle());
        this.setPriority(entity.getPriority());
    }

    public Integer getKey() {
        return this.getId();
    }

    @Override
    public int compareTo(TaxonomyItem o) {
        return this.getPriority() - o.getPriority();
    }
}
