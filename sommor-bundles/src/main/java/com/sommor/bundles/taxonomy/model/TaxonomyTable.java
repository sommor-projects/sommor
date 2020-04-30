package com.sommor.bundles.taxonomy.model;

import com.sommor.bundles.taxonomy.component.key.TaxonomyKeyField;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.core.component.table.OnTableRowFill;
import com.sommor.core.model.Model;
import com.sommor.core.component.table.EntityTable;
import com.sommor.core.spring.ApplicationContextHolder;
import com.sommor.core.view.field.text.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
public class TaxonomyTable extends EntityTable implements OnTableRowFill {

    @Getter @Setter
    @TaxonomyKeyField
    private String key;

    @Getter @Setter
    @TextField
    private String name;

    @Getter @Setter
    @TextField
    private String title;

    @Getter @Setter
    @TextField
    private String subTitle;

    @Getter @Setter
    @TextField
    private Integer priority;

    @Getter @Setter
    @TextField
    private Boolean highestPriority;

    @Getter @Setter
    @TextField
    private Boolean lowestPriority;

    @Getter @Setter
    @TextField
    private Integer subTaxonomyCount;

    private TaxonomyRepository taxonomyRepository = ApplicationContextHolder.getBean(TaxonomyRepository.class);

    @Override
    public void onTableRowFill(int row, int total, Model model) {
        this.highestPriority = row == 0;
        this.lowestPriority = row == total - 1;

        TaxonomyEntity entity = model.getTarget();
        this.setSubTaxonomyCount(taxonomyRepository.countByParent(entity.getName(), entity.isRoot() ? entity.getName() : entity.getType()));
    }
}
