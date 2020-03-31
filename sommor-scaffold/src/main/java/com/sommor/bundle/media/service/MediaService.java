package com.sommor.bundle.media.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.curd.CurdService;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.bundle.media.entity.MediaFileEntity;
import com.sommor.bundle.media.entity.MediaFileSubjectRelationEntity;
import com.sommor.bundle.media.component.file.MediaFile;
import com.sommor.bundle.media.model.UploadedFile;
import com.sommor.bundle.media.repository.MediaFileRepository;
import com.sommor.bundle.media.repository.MediaFileSubjectRelationRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Service
public class MediaService extends CurdService<MediaFileEntity> {

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
            mediaFileRepository.save(entity);
        }

        MediaFile mediaFile = new MediaFile();
        mediaFile.setId(entity.getId());
        mediaFile.setUri(entity.getUri());

        String url = this.parseUrl(entity.getUri());
        mediaFile.setUrl(url);

        mediaFile.setThumbUrl(url);
        mediaFile.setTitle(originalFileName);
        mediaFile.setMimeType(entity.getMimeType());
        mediaFile.setFileSize(entity.getFileSize());

        return mediaFile;
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
