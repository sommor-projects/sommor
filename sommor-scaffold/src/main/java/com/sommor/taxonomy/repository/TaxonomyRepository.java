package com.sommor.taxonomy.repository;

import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.taxonomy.entity.TaxonomyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Mapper
public interface TaxonomyRepository extends CurdRepository<TaxonomyEntity> {

    default List<TaxonomyEntity> findRootTaxonomies() {
        return this.findTaxonomiesByRootId(0);
    }

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<TaxonomyEntity> findTaxonomiesByRootId(Integer rootId);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<TaxonomyEntity> findTaxonomiesByParentId(Integer parentId);
}
