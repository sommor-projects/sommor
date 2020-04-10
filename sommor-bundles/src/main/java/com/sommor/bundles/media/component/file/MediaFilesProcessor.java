package com.sommor.bundles.media.component.file;

import com.sommor.bundles.media.entity.MediaFileEntity;
import com.sommor.bundles.media.entity.MediaFileSubjectRelationEntity;
import com.sommor.bundles.media.repository.MediaFileRepository;
import com.sommor.bundles.media.repository.MediaFileSubjectRelationRepository;
import com.sommor.bundles.media.service.MediaService;
import com.sommor.core.component.form.FieldSaveContext;
import com.sommor.core.component.form.extension.FormFieldSavedProcessor;
import com.sommor.core.component.form.extension.FormFieldSavingProcessor;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.Model;
import com.sommor.core.model.ModelField;
import com.sommor.core.model.fill.FieldFillContext;
import com.sommor.core.model.fill.FieldFillProcessor;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityFieldDefinition;
import com.sommor.mybatis.sql.field.type.Array;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/4
 */
@Implement
public class MediaFilesProcessor implements
        FieldFillProcessor<MediaFilesFieldConfig>,
        FormFieldSavingProcessor<MediaFilesFieldConfig>,
        FormFieldSavedProcessor<MediaFilesFieldConfig> {

    @Resource
    private MediaService mediaService;

    @Resource
    private MediaFileSubjectRelationRepository mediaFileSubjectRelationRepository;

    @Resource
    private MediaFileRepository mediaFileRepository;


    @Override
    public Object processOnFieldFill(MediaFilesFieldConfig config, FieldFillContext ctx) {
        ModelField modelField = ctx.getModelField();

        Model sourceModel = ctx.getSourceModel();

        Class expectType = modelField.getType();
        if (expectType == MediaFiles.class) {
            return parseMediaFiles(config, modelField, sourceModel);
        } else {
            String cover = config.getCover();
            if (expectType == String.class) {
                return parseMediaFileUrl(cover);
            }
        }

        return null;
    }

    private String parseMediaFileUrl(String uri) {
        return mediaService.parseUrl(uri);
    }

    private MediaFiles parseMediaFiles(MediaFilesFieldConfig config, ModelField modelField, Model sourceModel) {
        int maxCount = config.getMaxCount();
        MediaFiles mediaFiles = new MediaFiles();

        Integer entityId = config.getEntityId();

        if (entityId != null && entityId > 0) {
            String entityName = config.getEntityName();
            String subjectGroup = modelField.getName();

            List<MediaFileSubjectRelationEntity> subjectEntities = mediaFileSubjectRelationRepository.findBySubject(entityName, subjectGroup, entityId);
            if (CollectionUtils.isNotEmpty(subjectEntities)) {
                List<Integer> ids = subjectEntities.stream().map(p -> p.getMediaFileId()).collect(Collectors.toList());
                List<MediaFileEntity> fileEntities = mediaFileRepository.findByIds(new Array(ids));
                Map<Integer, MediaFileEntity> map = fileEntities.stream().collect(Collectors.toMap(p -> p.getId(), p -> p));
                for (int i=0; i<maxCount && i<subjectEntities.size(); i++) {
                    MediaFileSubjectRelationEntity subjectEntity = subjectEntities.get(i);
                    MediaFileEntity mediaFileEntity = map.get(subjectEntity.getMediaFileId());

                    MediaFile mediaFile = convert(mediaFileEntity, subjectEntity);
                    mediaFiles.add(mediaFile);
                }
            }
        }

        return mediaFiles;
    }

    private MediaFile convert(MediaFileEntity mediaFileEntity, MediaFileSubjectRelationEntity subjectEntity) {
        MediaFile mediaFile = new MediaFile();

        mediaFile.setId(mediaFileEntity.getId());
        mediaFile.setMediaSubjectRelationId(subjectEntity.getId());

        mediaFile.setUri(mediaFileEntity.getUri());

        String url = mediaService.parseUrl(mediaFileEntity.getUri());
        mediaFile.setUrl(url);

        mediaFile.setThumbUrl(url);
        mediaFile.setTitle(subjectEntity.getTitle());
        mediaFile.setMimeType(mediaFileEntity.getMimeType());
        mediaFile.setPriority(subjectEntity.getPriority());

        return mediaFile;
    }


    @Override
    public void processOnFormSaving(MediaFilesFieldConfig config, FieldSaveContext ctx) {
        MediaFiles mediaFiles = ctx.getFieldValue();
        if (null == mediaFiles || mediaFiles.isEmpty()) {
            return;
        }

        String coverFieldName = config.getCoverFieldName();
        BaseEntity entity = ctx.getEntity();

        EntityDefinition definition = entity.definition();
        EntityFieldDefinition efd = definition.getFieldDefinition(coverFieldName);
        if (null != efd) {
            String uri =  mediaFiles.size() > 0 ? mediaFiles.get(0).getUri() : "";
            entity.setFieldValue(efd, uri);
        }
    }

    @Override
    public void processOnFormSaved(MediaFilesFieldConfig config, FieldSaveContext ctx) {
        MediaFiles mediaFiles = ctx.getFieldValue();

        this.saveMediaFiles(mediaFiles);

        BaseEntity entity = ctx.getEntity();
        String subjectName = entity.definition().getSubjectName();

        Map<Integer, MediaFile> map = null == mediaFiles ? Collections.emptyMap() : mediaFiles.stream()
                .filter(p -> null != p.getMediaSubjectRelationId())
                .collect(Collectors.toMap(p -> p.getMediaSubjectRelationId(), p -> p));

        Integer subjectId = entity.getId();
        String subjectGroup = ctx.getModelField().getName();

        List<MediaFileSubjectRelationEntity> subjectEntities = mediaFileSubjectRelationRepository.findBySubject(subjectName, subjectGroup, subjectId);
        if (CollectionUtils.isNotEmpty(subjectEntities)) {
            for (MediaFileSubjectRelationEntity e : subjectEntities) {
                if (! map.containsKey(e.getId())) {
                    mediaFileSubjectRelationRepository.deleteById(e.getId());
                }
            }
        }

        if (CollectionUtils.isNotEmpty(mediaFiles)) {
            int i = 0;
            for (MediaFile mediaFile : mediaFiles) {
                if (mediaFile.getMediaSubjectRelationId() == null) {
                    MediaFileSubjectRelationEntity relationEntity = new MediaFileSubjectRelationEntity();
                    relationEntity.setSubject(subjectName);
                    relationEntity.setSubjectGroup(subjectGroup);
                    relationEntity.setSubjectId(subjectId);
                    relationEntity.setMediaFileId(mediaFile.getId());
                    relationEntity.setTitle(mediaFile.getTitle());
                    relationEntity.setPriority(i);
                    mediaFileSubjectRelationRepository.insert(relationEntity);
                } else {
                    mediaFileSubjectRelationRepository.updatePriorityById(mediaFile.getMediaSubjectRelationId(), i);
                }
                i++;
            }
        }
    }

    private void saveMediaFiles(MediaFiles mediaFiles) {
        if (CollectionUtils.isNotEmpty(mediaFiles)) {
            for (MediaFile mediaFile : mediaFiles) {
                if (null == mediaFile.getId() || mediaFile.getId() == 0) {
                    MediaFileEntity entity = mediaFileRepository.findByUri(mediaFile.getUri());
                    if (null == entity) {
                        entity = new MediaFileEntity();
                        entity.setUri(mediaFile.getUri());
                        mediaService.save(entity);
                    }
                    mediaFile.setId(entity.getId());
                }
            }
        }
    }
}
