package com.sommor.bundles.taxonomy.view;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundles.taxonomy.entity.SubjectTaxonomyRelationEntity;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.view.fields.taxonomy.select.TaxonomySelect;
import com.sommor.bundles.taxonomy.view.fields.taxonomy.select.TaxonomySelectView;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.param.EntityQueryParam;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import com.sommor.core.view.field.FieldRenderContext;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/28
 */
public class SubjectTaxonomyQueryParam extends EntityQueryParam {

    @Getter
    @Setter
    private String taxonomy;

    @Getter
    @Setter
    @TaxonomySelect(title = "分类", selfSelection = true)
    private Integer taxonomyId;

    private String subject;

    private TaxonomyRepository taxonomyRepository = ApplicationContextHolder.getBean(TaxonomyRepository.class);

    public SubjectTaxonomyQueryParam(String subject) {
        this.subject = subject;
    }

    @Override
    public void onQuery(Query query) {
        super.onQuery(query);

        Integer taxonomyId = this.getTaxonomyId();

        if (null == taxonomyId) {
            String taxonomy = this.getTaxonomy();
            if (StringUtils.isNotBlank(taxonomy)) {
                if (StringUtils.isNumeric(taxonomy)) {
                    taxonomyId = Integer.valueOf(taxonomy);
                } else {
                    TaxonomyEntity taxonomyEntity = taxonomyRepository.findByName(taxonomy);
                    if (null == taxonomyEntity) {
                        throw new ErrorCodeException(ErrorCode.of("subject.query.taxonomy.invalid", taxonomy));
                    }

                    taxonomyId = taxonomyEntity.getId();
                }
            }

            this.setTaxonomyId(taxonomyId);
        }

        if (null == taxonomyId) {
            throw new ErrorCodeException(ErrorCode.of("subject.query.taxonomy.empty"));
        }

        String subject = this.subject;
        query.leftJoin(
                SubjectTaxonomyRelationEntity.class,
                "taxonomy_subject.subject='"+subject+"' and taxonomy_subject.subject_id="+subject+".id"
        );

        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(taxonomyId);
        int size = paths.size();
        if (size == 1) {
            query.where().condition().and("typeId", "taxonomy_subject.type_id", taxonomyId);
        } else {
            String taxonomyFieldName = "taxonomy_id" + (size-1);
            query.where().condition().and(taxonomyFieldName, "taxonomy_subject." + taxonomyFieldName, taxonomyId);
        }
    }

    public void renderTaxonomyId(FieldRenderContext ctx) {
        TaxonomySelectView view = ctx.getFieldView();
        Integer taxonomyId = this.getTaxonomyId();
        TaxonomyEntity taxonomyEntity =  taxonomyRepository.findById(taxonomyId);
        view.setTypeId(taxonomyEntity.getTypeId() == 0 ? taxonomyEntity.getId() : taxonomyEntity.getTypeId());
        view.tree();
    }
}
