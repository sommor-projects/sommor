package com.sommor.media.repository;

import com.sommor.media.entity.MediaFileSubjectRelationEntity;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Mapper
public interface MediaFileSubjectRelationRepository extends CurdRepository<MediaFileSubjectRelationEntity> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<MediaFileSubjectRelationEntity> findBySubject(String subject, Integer subjectId);

    @DeleteProvider(type = SqlProvider.class, method = "deleteBy")
    void deleteByMediaFileId(Integer mediaFileId);
}
