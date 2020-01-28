package com.sommor.bundles.taxonomy.view;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.fields.conditional.Conditional;
import com.sommor.scaffold.param.EntitySearchParam;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/28
 */
public class SubjectTaxonomySearchParam extends EntitySearchParam {

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    @Conditional
    private Integer taxonomy;

    @Override
    public void onQuery(Query query) {
        super.onQuery(query);

        Integer taxonomy = this.getTaxonomy();
        if (null == taxonomy) {
            if (StringUtils.isNotBlank(this.getType())) {
                TaxonomyEntity taxonomyEntity =  ApplicationContextHolder.getBean(TaxonomyRepository.class).findByType(this.getType());
                if (null == taxonomyEntity) {
                    throw new ErrorCodeException(ErrorCode.of("subject.query.type.invalid", this.getType()));
                }
                taxonomy = taxonomyEntity.getId();
            }
        }

        if (null == taxonomy) {
            throw new ErrorCodeException(ErrorCode.of("subject.query.taxonomy.empty"));
        }

        this.setTaxonomy(taxonomy);
    }
}
