package com.sommor.taxonomy.repository;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.mybatis.sql.select.OrderType;
import com.sommor.taxonomy.entity.TaxonomyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Mapper
public interface TaxonomyRepository extends CurdRepository<TaxonomyEntity> {

    default List<TaxonomyEntity> findRootTaxonomies() {
        Query query = new Query()
                .where("typeId", 0)
                .orderly("priority", OrderType.ASC);

        return this.find(query);
    }

    default TaxonomyEntity findLowestPriority(Integer parentId) {
        Query query = new Query()
                .where("parentId", parentId)
                .orderly("priority", OrderType.DESC);

        return this.findFirst(query);
    }

    default List<TaxonomyEntity> findTaxonomyPaths(Integer id) {
        Integer taxonomyId = id;
        LinkedList<TaxonomyEntity> paths = new LinkedList<>();
        while (taxonomyId != 0) {
            TaxonomyEntity entity = this.findById(taxonomyId);
            if (null == entity) {
                throw new ErrorCodeException(ErrorCode.of("taxonomy.id.invalid", id));
            }
            paths.addFirst(entity);
            taxonomyId = entity.getParentId();
        }
        return paths;
    }

    default TaxonomyEntity findByType(String type) {
        return this.findBySlug(0, type);
    }

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    TaxonomyEntity findByName(String name);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    TaxonomyEntity findBySlug(Integer typeId, String slug);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<TaxonomyEntity> findByTypeId(Integer typeId);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<TaxonomyEntity> findByParentId(Integer parentId);

    @SelectProvider(type = SqlProvider.class, method = "countBy")
    int countByParentId(Integer parentId);

    @UpdateProvider(type = SqlProvider.class, method = "updateBy")
    int updatePriorityById(Integer id, Integer priority);
}
