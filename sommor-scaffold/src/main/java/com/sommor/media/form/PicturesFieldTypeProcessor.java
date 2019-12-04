package com.sommor.media.form;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.extensibility.config.Implement;
import com.sommor.media.entity.MediaFileEntity;
import com.sommor.media.entity.MediaFileSubjectRelationEntity;
import com.sommor.media.model.MediaFileItem;
import com.sommor.media.repository.MediaFileRepository;
import com.sommor.media.repository.MediaFileSubjectRelationRepository;
import com.sommor.media.service.MediaFileProcessor;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.sql.field.type.Array;
import com.sommor.taxonomy.entity.SubjectBasedEntity;
import com.sommor.view.FormView;
import com.sommor.view.form.EntityForm;
import com.sommor.view.form.FieldTypeProcessor;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/4
 */
@Implement
public class PicturesFieldTypeProcessor implements FieldTypeProcessor<PicturesField> {

    @Resource
    private MediaFileSubjectRelationRepository mediaFileSubjectRelationRepository;

    @Resource
    private MediaFileRepository mediaFileRepository;

    private MediaFileProcessor mediaFileProcessor = ExtensionExecutor.proxyOf(MediaFileProcessor.class);

    @Override
    public void processOnFormRender(PicturesField picturesField, BaseEntity entity, EntityForm form, FormView formView) {
        if (entity.getId() != null && entity.getId() > 0 && entity instanceof SubjectBasedEntity) {
            SubjectBasedEntity subjectBasedEntity = (SubjectBasedEntity) entity;
            String subject = subjectBasedEntity.subject();
            Integer subjectId = entity.getId();

            List<MediaFileItem> items = new ArrayList<>();

            List<MediaFileSubjectRelationEntity> subjectEntities = mediaFileSubjectRelationRepository.findBySubject(subject, subjectId);
            if (CollectionUtils.isNotEmpty(subjectEntities)) {
                List<Integer> ids = subjectEntities.stream().map(p -> p.getMediaFileId()).collect(Collectors.toList());
                List<MediaFileEntity> fileEntities = mediaFileRepository.findByIds(new Array(ids));
                Map<Integer, MediaFileEntity> map = fileEntities.stream().collect(Collectors.toMap(p -> p.getId(), p -> p));
                for (MediaFileSubjectRelationEntity subjectEntity : subjectEntities) {
                    MediaFileEntity mediaFileEntity = map.get(subjectEntity.getMediaFileId());

                    MediaFileItem item = new MediaFileItem();
                    item.setId(mediaFileEntity.getId());
                    item.setMediaSubjectRelationId(subjectEntity.getId());

                    item.setUri(mediaFileEntity.getUri());

                    String url = mediaFileProcessor.parseUrl(mediaFileEntity.getUri());
                    item.setUrl(url);

                    item.setThumbUrl(url);
                    item.setTitle(mediaFileEntity.getTitle());
                    item.setMimeType(mediaFileEntity.getMimeType());
                    item.setPriority(subjectEntity.getPriority());

                    items.add(item);
                }
            }

            picturesField.addAll(items);
        }
    }

    @Override
    public void processOnFormSave(PicturesField value, BaseEntity entity, BaseEntity originEntity, EntityForm form) {
        SubjectBasedEntity subjectBasedEntity = (SubjectBasedEntity) entity;
        if (CollectionUtils.isNotEmpty(value)) {
            String subject = subjectBasedEntity.subject();
            Integer subjectId = subjectBasedEntity.getId();

            Map<Integer, MediaFileItem> map = value.stream()
                    .filter(p -> null != p.getMediaSubjectRelationId())
                    .collect(Collectors.toMap(p -> p.getMediaSubjectRelationId(), p -> p));

            List<MediaFileSubjectRelationEntity> subjectEntities = mediaFileSubjectRelationRepository.findBySubject(subject, subjectId);
            if (CollectionUtils.isNotEmpty(subjectEntities)) {
                for (MediaFileSubjectRelationEntity e : subjectEntities) {
                    if (! map.containsKey(e.getId())) {
                        mediaFileSubjectRelationRepository.deleteById(e.getId());
                    }
                }
            }

            for (MediaFileItem item : value) {
                if (null != item.getId() && item.getMediaSubjectRelationId() == null) {
                    MediaFileSubjectRelationEntity relationEntity = new MediaFileSubjectRelationEntity();
                    relationEntity.setSubject(subject);
                    relationEntity.setSubjectId(subjectId);
                    relationEntity.setMediaFileId(item.getId());
                    mediaFileSubjectRelationRepository.insert(relationEntity);
                }
            }
        }
    }
}
