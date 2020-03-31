package com.sommor.bundle.taxonomy.entity;

import com.sommor.component.configurable.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Table("taxonomies")
@Getter @Setter
public class TaxonomyEntity extends ConfigurableEntity implements Comparable<TaxonomyEntity> {

    @Column
    private String name;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private Integer typeId;

    /**
     * 父分类ID，最顶层的分类该值为0
     */
    @Column
    private Integer parentId;

    @Column
    private Integer priority;

    @Column
    private String group;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[id=" + this.getId() + ", name=" + this.getSubTitle() + ", title=" + this.getTitle() + "]";
    }

    @Override
    public int compareTo(TaxonomyEntity o) {
        return this.getPriority() - o.getPriority();
    }
}
