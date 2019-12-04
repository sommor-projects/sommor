package com.sommor.taxonomy.model;

import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.view.OptionTree;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-16
 */
public class TaxonomyTree extends TaxonomyItem {

    @Setter
    @Getter
    private List<TaxonomyTree> children;

    public TaxonomyTree() {
    }
    public TaxonomyTree(TaxonomyEntity entity) {
        from(entity);
    }

    public OptionTree toOptionTree() {
        OptionTree tree = new OptionTree();
        tree.setValue(String.valueOf(this.getId()));
        tree.setTitle(this.getTitle() + "(" + this.getName() + ")");

        if (null != children) {
            for (TaxonomyTree taxonomyTree : children) {
                tree.addChild(taxonomyTree.toOptionTree());
            }
        }

        return tree;
    }
}
