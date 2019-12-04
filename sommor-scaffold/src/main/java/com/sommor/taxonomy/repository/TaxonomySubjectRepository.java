package com.sommor.taxonomy.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.taxonomy.entity.TaxonomySubjectRelationEntity;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/3
 */
public interface TaxonomySubjectRepository extends CurdRepository<TaxonomySubjectRelationEntity> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<TaxonomySubjectRelationEntity> findBySubject(String subject, Integer subjectId);

    @DeleteProvider(type = SqlProvider.class, method = "deleteBy")
    void deleteBySubject(String subject, Integer subjectId);
}
