package com.sommor.bundle.taxonomy.model;

import com.sommor.bundle.taxonomy.repository.TaxonomyRepository;
import com.sommor.component.table.OnTableRowFill;
import com.sommor.model.Model;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.component.table.EntityTable;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import com.sommor.view.field.text.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
public class TaxonomyTable extends EntityTable implements OnTableRowFill {

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

        BaseEntity entity = model.getTarget();
        this.setSubTaxonomyCount(taxonomyRepository.countByParentId(entity.getId()));
    }
}
