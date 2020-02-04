package com.sommor.bundles.media.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundles.media.view.MediaFileTable;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.bundles.media.entity.MediaFileEntity;
import com.sommor.bundles.media.entity.MediaFileSubjectRelationEntity;
import com.sommor.bundles.media.fields.file.MediaFile;
import com.sommor.bundles.media.model.UploadedFile;
import com.sommor.bundles.media.repository.MediaFileRepository;
import com.sommor.bundles.media.repository.MediaFileSubjectRelationRepository;
import com.sommor.core.view.field.EntityForm;
import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.scaffold.param.EntityQueryParam;
import com.sommor.core.curd.CurdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Service
public class MediaService extends CurdService<
        MediaFileEntity,
        EntityForm,
        EntityFormRenderParam,
        MediaFileEntity,
        EntityDetailParam,
        MediaFileTable,
        EntityQueryParam> {

    private MediaFileProcessor mediaFileProcessor = ExtensionExecutor.proxyOf(MediaFileProcessor.class);

    @Resource
    private MediaFileRepository mediaFileRepository;

    @Resource
    private MediaFileSubjectRelationRepository mediaFileSubjectRelationRepository;

    public String parseUrl(String uri) {
        if (null == uri) {
            return null;
        }

        String parsedUrl = mediaFileProcessor.parseUrl(uri);
        return parsedUrl == null ? uri : parsedUrl;
    }

    public MediaFile upload(InputStream is, String originalFileName) {
        UploadedFile uploadedFile = mediaFileProcessor.upload(is, originalFileName);
        if (null == uploadedFile) {
            throw new ErrorCodeException(ErrorCode.of("media.uploader.unknown"));
        }

        MediaFileEntity entity = mediaFileRepository.findByUri(uploadedFile.getUri());
        if (null == entity) {
            entity = new MediaFileEntity();
            entity.setMimeType(uploadedFile.getMimeType());
            entity.setUri(uploadedFile.getUri());
            this.save(entity);
        }

        MediaFile item = new MediaFile();
        item.setId(entity.getId());
        item.setUri(entity.getUri());

        String url = mediaFileProcessor.parseUrl(entity.getUri());
        item.setUrl(url);

        item.setThumbUrl(url);
        item.setTitle(originalFileName);
        item.setMimeType(entity.getMimeType());
        item.setFileSize(entity.getFileSize());

        return item;
    }

    public void saveSubject(String url, String subject, Integer subjectId) {
        MediaFileEntity entity = mediaFileRepository.findByUri(url);
        if (null == entity) {
            throw new ErrorCodeException(ErrorCode.of("media.url.invalid", url));
        }

        MediaFileSubjectRelationEntity subjectEntity = new MediaFileSubjectRelationEntity();
        subjectEntity.setMediaFileId(entity.getId());
        subjectEntity.setSubject(subject);
        subjectEntity.setSubjectId(subjectId);

        mediaFileSubjectRelationRepository.save(subjectEntity);
    }

    public void deleteUrl(String url) {
        MediaFileEntity entity = mediaFileRepository.findByUri(url);
        if (null == entity) {
            throw new ErrorCodeException(ErrorCode.of("media.url.invalid", url));
        }

        mediaFileRepository.deleteById(entity.getId());
        mediaFileSubjectRelationRepository.deleteByMediaFileId(entity.getId());
    }
}
