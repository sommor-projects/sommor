package com.sommor.bundles.taxonomy.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.bundles.taxonomy.entity.SubjectTaxonomyRelationEntity;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/3
 */
@Mapper
public interface TaxonomySubjectRepository extends CurdRepository<SubjectTaxonomyRelationEntity> {

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<SubjectTaxonomyRelationEntity> findBySubject(String subject, Integer subjectId);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<SubjectTaxonomyRelationEntity> findBySubjectAndType(String subject, Integer subjectId, Integer typeId);

    @DeleteProvider(type = SqlProvider.class, method = "deleteBy")
    void deleteBySubject(String subject, Integer subjectId);
}
