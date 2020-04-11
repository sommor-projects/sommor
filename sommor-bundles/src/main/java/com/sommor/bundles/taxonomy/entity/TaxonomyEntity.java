package com.sommor.bundles.taxonomy.entity;

import com.google.common.base.Objects;
import com.sommor.core.component.configurable.ConfigurableEntity;
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

    public static final String ROOT = "_";

    @Column
    private String name;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private String type;

    @Column
    private String parent;

    @Column
    private Integer priority;

    @Column
    private String group;

    public String getKey() {
        if (ROOT.equals(type)) {
            return name;
        }

        return type + ":" + name;
    }

    public String getParentKey() {
        if (isRoot()) {
            return null;
        }

        if (type.equals(parent)) {
            return parent;
        }

        return type + ":" + parent;
    }

    public boolean isRoot() {
        return ROOT.equals(parent);
    }

    public boolean equals(String name, String type) {
        return this.getName().equals(name) && this.getType().equals(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaxonomyEntity)) return false;
        if (!super.equals(o)) return false;
        TaxonomyEntity that = (TaxonomyEntity) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), name, type);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[id=" + this.getId() + ", name=" + this.getSubTitle() + ", title=" + this.getTitle() + "]";
    }

    @Override
    public int compareTo(TaxonomyEntity o) {
        return this.getPriority() - o.getPriority();
    }
}
