package com.sommor.bundles.taxonomy.model;

import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.core.component.form.field.TreeOption;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

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
        String label;

        if (StringUtils.isNotBlank(this.getTitle()) && StringUtils.isNotBlank(this.getSubTitle())) {
            if (this.getSubTitle().equals(this.getTitle())) {
                label = this.getTitle();
            } else {
                label = this.getTitle() + "(" + this.getSubTitle() + ")";
            }
        } else if (StringUtils.isNotBlank(this.getTitle())) {
            label = this.getTitle();
        } else if (StringUtils.isNotBlank(this.getSubTitle())) {
            label = this.getSubTitle();
        } else {
            label = this.getName();
        }

        TreeOption tree = new TreeOption(label, this.getName());

        if (null != children) {
            for (TaxonomyTree taxonomyTree : children) {
                tree.addChild(taxonomyTree.toTreeOption());
            }
        }

        return tree;
    }
}
