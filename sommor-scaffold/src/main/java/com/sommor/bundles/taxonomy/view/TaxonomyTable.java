package com.sommor.bundles.taxonomy.view;

import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.scaffold.view.field.DataSource;
import com.sommor.scaffold.view.field.EntityTable;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import com.sommor.scaffold.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
public class TaxonomyTable extends EntityTable {

    @Getter @Setter
    @TextField
    private String slug;

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
    public void onTableRender(int row, int total, DataSource dataSource) {
        super.onTableRender(row, total, dataSource);

        this.highestPriority = row == 0;
        this.lowestPriority = row == total - 1;

        BaseEntity entity = dataSource.getTarget();
        this.setSubTaxonomyCount(taxonomyRepository.countByParentId(entity.getId()));
    }
}