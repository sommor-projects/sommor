package com.sommor.taxonomy.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.taxonomy.entity.AttributeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Mapper
public interface AttributeRepository extends CurdRepository<AttributeEntity> {


}
