package com.sommor.taxonomy.model;

import com.sommor.taxonomy.entity.TaxonomyEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-16
 */
@Getter
@Setter
public class TaxonomyTree implements Comparable<TaxonomyTree> {

    private Integer id;

    private String name;

    private String title;

    private Integer priority;

    private List<TaxonomyTree> children;

    public TaxonomyTree() {
    }

    public TaxonomyTree(TaxonomyEntity entity) {
        this.setId(entity.getId());
        this.setName(entity.getName());
        this.setTitle(entity.getTitle());
        this.setPriority(entity.getPriority());
    }

    public String getKey() {
        return String.valueOf(id);
    }

    @Override
    public int compareTo(TaxonomyTree o) {
        return o.getPriority() - this.getPriority();
    }
}
