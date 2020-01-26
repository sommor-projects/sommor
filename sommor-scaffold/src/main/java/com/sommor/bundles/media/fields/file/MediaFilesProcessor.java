package com.sommor.bundles.media.fields.file;

import com.sommor.bundles.media.entity.MediaFileEntity;
import com.sommor.bundles.media.entity.MediaFileSubjectRelationEntity;
import com.sommor.bundles.media.repository.MediaFileRepository;
import com.sommor.bundles.media.repository.MediaFileSubjectRelationRepository;
import com.sommor.bundles.media.service.MediaFileProcessor;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityFieldDefinition;
import com.sommor.mybatis.sql.field.type.Array;
import com.sommor.scaffold.view.field.FieldContext;
import com.sommor.scaffold.view.field.FieldRenderContext;
import com.sommor.scaffold.view.field.FieldProcessor;
import com.sommor.scaffold.view.field.FieldSaveContext;
import com.sommor.scaffold.view.field.FieldDefinition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
public class MediaFilesProcessor implements FieldProcessor<MediaFilesField> {

    @Resource
    private MediaFileSubjectRelationRepository mediaFileSubjectRelationRepository;

    @Resource
    private MediaFileRepository mediaFileRepository;

    private MediaFileProcessor mediaFileProcessor = ExtensionExecutor.proxyOf(MediaFileProcessor.class);


    @Override
    public void processOnFormRender(MediaFilesField mediaFilesField, FieldRenderContext ctx) {
        MediaFilesView mediaFilesView = (MediaFilesView) ctx.getFieldView();
        mediaFilesView.setMaxCount(mediaFilesField.maxCount());
    }

    @Override
    public Object processOnFill(MediaFilesField mediaFilesField, FieldContext ctx) {
        FieldDefinition fd = ctx.getDefinition();
        BaseEntity entity = (ctx.getData().getTarget() instanceof BaseEntity) ? ctx.getData().getTarget() : null;

        Class expectType = fd.getField().getType();
        if (expectType == MediaFiles.class) {
            return parseMediaFiles(mediaFilesField, fd, entity);
        } else if (null != entity) {
            Object entityFieldValue = ctx.getFieldValue();
            if (entityFieldValue instanceof String) {
                if (expectType == String.class) {
                    return parseMediaFileUrl((String) entityFieldValue);
                }
            }
        }

        return null;
    }

    private String parseMediaFileUrl(String uri) {
        return mediaFileProcessor.parseUrl(uri);
    }

    private MediaFiles parseMediaFiles(MediaFilesField mediaFilesField, FieldDefinition fd, BaseEntity entity) {
        int maxCount = mediaFilesField.maxCount();
        MediaFiles mediaFiles = new MediaFiles();

        if (entity != null && entity.getId() != null && entity.getId() > 0) {
            String subject = entity.definition().getSubjectName();

            String subjectName = subject;
            Integer subjectId = entity.getId();
            String subjectGroup = fd.getName();

            List<MediaFileSubjectRelationEntity> subjectEntities = mediaFileSubjectRelationRepository.findBySubject(subjectName, subjectGroup, subjectId);
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

        String url = mediaFileProcessor.parseUrl(mediaFileEntity.getUri());
        mediaFile.setUrl(url);

        mediaFile.setThumbUrl(url);
        mediaFile.setTitle(subjectEntity.getTitle());
        mediaFile.setMimeType(mediaFileEntity.getMimeType());
        mediaFile.setPriority(subjectEntity.getPriority());

        return mediaFile;
    }

    @Override
    public void processOnFormSaving(MediaFilesField mediaFilesField, FieldSaveContext ctx) {
        MediaFiles mediaFiles = ctx.getFieldValue();

        String entityField = mediaFilesField.entityField();
        if (StringUtils.isBlank(entityField)) {
            entityField = ctx.getDefinition().getName();
        }

        BaseEntity entity = ctx.getEntity();

        EntityDefinition definition = entity.definition();
        EntityFieldDefinition efd = definition.getFieldDefinition(entityField);
        if (null != efd) {
            String uri =  mediaFiles.size() > 0 ? mediaFiles.get(0).getUri() : "";
            entity.setFieldValue(efd, uri);
        }
    }

    @Override
    public void processOnFormSaved(MediaFilesField mediaFilesField, FieldSaveContext ctx) {
        MediaFiles mediaFiles = ctx.getFieldValue();

        BaseEntity entity = ctx.getEntity();
        String subjectName = entity.definition().getSubjectName();

        Map<Integer, MediaFile> map = null == mediaFiles ? Collections.emptyMap() : mediaFiles.stream()
                .filter(p -> null != p.getMediaSubjectRelationId())
                .collect(Collectors.toMap(p -> p.getMediaSubjectRelationId(), p -> p));

        Integer subjectId = entity.getId();
        String subjectGroup = ctx.getDefinition().getName();

        List<MediaFileSubjectRelationEntity> subjectEntities = mediaFileSubjectRelationRepository.findBySubject(subjectName, subjectGroup, subjectId);
        if (CollectionUtils.isNotEmpty(subjectEntities)) {
            for (MediaFileSubjectRelationEntity e : subjectEntities) {
                if (! map.containsKey(e.getId())) {
                    mediaFileSubjectRelationRepository.deleteById(e.getId());
                }
            }
        }

        int i = 0;
        for (MediaFile item : mediaFiles) {
            if (item.getMediaSubjectRelationId() == null) {
                MediaFileSubjectRelationEntity relationEntity = new MediaFileSubjectRelationEntity();
                relationEntity.setSubject(subjectName);
                relationEntity.setSubjectGroup(subjectGroup);
                relationEntity.setSubjectId(subjectId);
                relationEntity.setMediaFileId(item.getId());
                relationEntity.setTitle(item.getTitle());
                relationEntity.setPriority(i);
                mediaFileSubjectRelationRepository.insert(relationEntity);
            } else {
                mediaFileSubjectRelationRepository.updatePriorityById(item.getMediaSubjectRelationId(), i);
            }
            i++;
        }
    }
}
