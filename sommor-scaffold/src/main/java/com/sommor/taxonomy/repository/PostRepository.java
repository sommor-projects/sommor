package com.sommor.taxonomy.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.taxonomy.entity.PostEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
@Mapper
public interface PostRepository extends CurdRepository<PostEntity> {
}
