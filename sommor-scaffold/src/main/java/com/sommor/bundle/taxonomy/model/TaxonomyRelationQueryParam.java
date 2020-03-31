package com.sommor.bundle.taxonomy.model;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundle.taxonomy.component.select.TaxonomySelectFieldConfig;
import com.sommor.bundle.taxonomy.component.select.TaxonomySelectField;
import com.sommor.bundle.taxonomy.entity.SubjectTaxonomyRelationEntity;
import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.repository.TaxonomyRepository;
import com.sommor.model.Model;
import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.param.EntityQueryParam;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import com.sommor.view.context.ViewRenderContext;
import com.sommor.view.model.OnViewRender;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/1/28
 */
public class TaxonomyRelationQueryParam extends EntityQueryParam implements OnViewRender {

    @Getter
    @Setter
    private String taxonomy;

    @Getter
    @Setter
    @TaxonomySelectField(title = "分类", includeSelf = true)
    private Integer taxonomyId;

    private String entityName;

    private TaxonomyRepository taxonomyRepository = ApplicationContextHolder.getBean(TaxonomyRepository.class);

    public TaxonomyRelationQueryParam(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public void onModelQuery(Query query) {
        super.onModelQuery(query);

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

        if (null != taxonomyId) {
            String subject = this.entityName;
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
    }

    @Override
    public void onViewRender(Model model, ViewRenderContext ctx) {
        TaxonomySelectFieldConfig taxonomySelectFieldConfig = model.getField("taxonomyId").getFieldConfig();
        Integer taxonomyId = this.getTaxonomyId();
        TaxonomyEntity taxonomyEntity =  taxonomyRepository.findById(taxonomyId);
        taxonomySelectFieldConfig.setTypeId(taxonomyEntity.getTypeId() == 0 ? taxonomyEntity.getId() : taxonomyEntity.getTypeId());
        taxonomySelectFieldConfig.setTree(true);
    }
}
