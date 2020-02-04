package com.sommor.bundles.taxonomy.model;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.core.view.TreeOption;
import lombok.Getter;
import lombok.Setter;

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

    public TreeOption toTreeOption() {
        String label = this.getTitle() + "(" + this.getSubTitle() + ")";
        TreeOption tree = new TreeOption(label, this.getId());

        if (null != children) {
            for (TaxonomyTree taxonomyTree : children) {
                tree.addChild(taxonomyTree.toTreeOption());
            }
        }

        return tree;
    }
}
