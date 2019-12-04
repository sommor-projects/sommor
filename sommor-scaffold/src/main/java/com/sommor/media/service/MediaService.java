package com.sommor.media.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.media.entity.MediaFileEntity;
import com.sommor.media.entity.MediaFileSubjectRelationEntity;
import com.sommor.media.model.MediaFileItem;
import com.sommor.media.model.UploadedFile;
import com.sommor.media.repository.MediaFileRepository;
import com.sommor.media.repository.MediaFileSubjectRelationRepository;
import com.sommor.mybatis.query.EntityQueryParam;
import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.scaffold.param.EntityInfoParam;
import com.sommor.scaffold.service.CurdService;
import com.sommor.view.form.EntityForm;
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
        EntityForm<MediaFileEntity>,
        EntityFormRenderParam,
        MediaFileEntity,
        EntityInfoParam,
        MediaFileEntity,
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

    public MediaFileItem upload(InputStream is, String originalFileName) {
        UploadedFile uploadedFile = mediaFileProcessor.upload(is, originalFileName);
        if (null == uploadedFile) {
            throw new ErrorCodeException(ErrorCode.of("media.uploader.unknown"));
        }

        MediaFileEntity entity = mediaFileRepository.findByUri(uploadedFile.getUri());
        if (null == entity) {
            entity = new MediaFileEntity();
            entity.setTitle(originalFileName);
            entity.setMimeType(uploadedFile.getMimeType());
            entity.setUri(uploadedFile.getUri());
            this.save(entity);
        }

        MediaFileItem item = new MediaFileItem();
        item.setId(entity.getId());
        item.setUri(entity.getUri());

        String url = mediaFileProcessor.parseUrl(entity.getUri());
        item.setUrl(url);

        item.setThumbUrl(url);
        item.setTitle(entity.getTitle());
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
