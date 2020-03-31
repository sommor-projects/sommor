package com.sommor.bundle.media.repository;

import com.sommor.bundle.media.entity.MediaFileEntity;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Mapper
public interface MediaFileRepository extends CurdRepository<MediaFileEntity> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    MediaFileEntity findByUri(String uri);

}
