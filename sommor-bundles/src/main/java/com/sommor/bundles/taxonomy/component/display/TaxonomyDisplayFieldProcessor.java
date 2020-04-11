package com.sommor.bundles.taxonomy.component.display;

import com.sommor.bundles.taxonomy.entity.SubjectTaxonomyRelationEntity;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundles.taxonomy.repository.TaxonomySubjectRepository;
import com.sommor.bundles.taxonomy.model.TaxonomyInfoVO;
import com.sommor.bundles.taxonomy.model.TaxonomySelectionVO;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
@Implement
public class TaxonomyDisplayFieldProcessor implements FieldFillProcessor<TaxonomyDisplayFieldConfig> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Resource
    private TaxonomySubjectRepository taxonomySubjectRepository;

    private void addTaxonomyItem(List<TaxonomyInfoVO> items, Long id) {
        TaxonomyInfoVO taxonomyInfoVO = parseTaxonomyItem(id);
        if (null != taxonomyInfoVO) {
            items.add(taxonomyInfoVO);
        }
    }
    private TaxonomyInfoVO parseTaxonomyItem(Long id) {
        if (null != id && id > 0) {
            TaxonomyEntity entity = taxonomyRepository.findById(id);
            return parseTaxonomyItem(entity);
        }

        return null;
    }

    private TaxonomyInfoVO parseTaxonomyItem(TaxonomyEntity entity) {
        TaxonomyInfoVO taxonomyInfoVO = new TaxonomyInfoVO();
        taxonomyInfoVO.setName(entity.getName());
        taxonomyInfoVO.setTitle(entity.getTitle());
        taxonomyInfoVO.setSubTitle(entity.getSubTitle());

        return taxonomyInfoVO;
    }

    @Override
    public Object processOnFieldFill(TaxonomyDisplayFieldConfig config, FieldFillContext ctx) {
        TaxonomySelectionVO taxonomySelectionVO = new TaxonomySelectionVO();

        String entityName = config.getEntityName();
        Long entityId = config.getEntityId();

        TaxonomyEntity type = taxonomyRepository.findByKey(config.getType());
        taxonomySelectionVO.setType(parseTaxonomyItem(type));

        List<TaxonomyInfoVO> items = new ArrayList<>();
        taxonomySelectionVO.setItems(items);

        List<SubjectTaxonomyRelationEntity> relationEntities = taxonomySubjectRepository.findBySubjectAndType(entityName, entityId, type.getId());
        if (CollectionUtils.isNotEmpty(relationEntities)) {
            if (config.isDisplayPaths()) {
                SubjectTaxonomyRelationEntity relationEntity = relationEntities.get(0);
                addTaxonomyItem(items, relationEntity.getTaxonomyId1());
                addTaxonomyItem(items, relationEntity.getTaxonomyId2());
                addTaxonomyItem(items, relationEntity.getTaxonomyId3());
                addTaxonomyItem(items, relationEntity.getTaxonomyId4());
                addTaxonomyItem(items, relationEntity.getTaxonomyId5());
            } else {
                for (SubjectTaxonomyRelationEntity relationEntity : relationEntities) {
                    addTaxonomyItem(items, relationEntity.getTaxonomyId());
                }
            }
        }

        return taxonomySelectionVO;
    }
}
