package com.sommor.bundle.taxonomy.repository;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundle.taxonomy.model.TaxonomyKey;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.mybatis.sql.select.OrderType;
import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
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

    default TaxonomyEntity findLowestPriority(String parent, String type) {
        Query query = new Query()
                .where("type", type)
                .where("parent", parent)
                .orderly("priority", OrderType.DESC);

        return this.findFirst(query);
    }

    default List<TaxonomyEntity> findTaxonomyPaths(String taxonomy, String type) {
        TaxonomyEntity entity = this.findByName(taxonomy, type);
        return this.findTaxonomyPaths(entity);
    }

    default List<TaxonomyEntity> findTaxonomyPaths(TaxonomyEntity entity) {
        LinkedList<TaxonomyEntity> paths = new LinkedList<>();
        while (true) {
            paths.addFirst(entity);
            if (entity.isRoot()) {
                break;
            } else {
                TaxonomyEntity parent = this.findParent(entity);
                if (null == parent) {
                    throw new ErrorCodeException(ErrorCode.of("taxonomy.entity.parent.absent", entity.getKey()));
                }
                entity = parent;
            }
        }
        return paths;
    }

    default TaxonomyEntity findByKey(String key) {
        TaxonomyKey taxonomyKey = TaxonomyKey.of(key);
        return this.findByKey(taxonomyKey);
    }

    default TaxonomyEntity findByKey(TaxonomyKey key) {
        return this.findByTypeAndName(key.getType(), key.getName());
    }

    default TaxonomyEntity findByType(String type) {
        return this.findByTypeAndName(TaxonomyEntity.ROOT, type);
    }

    default TaxonomyEntity findByName(String name, String type) {
        if (type == null || type.equals(name)) {
            return this.findByType(name);
        }

        return this.findByTypeAndName(type, name);
    }

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    TaxonomyEntity findByTypeAndName(String type, String name);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    TaxonomyEntity findBySubTitle(String type, String subTitle);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<TaxonomyEntity> findAllByType(String type);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<TaxonomyEntity> findAllByTypeAndGroup(String type, String group);

    default TaxonomyEntity findParent(TaxonomyEntity entity) {
        if (entity.isRoot()) {
            return null;
        }

        if (entity.getParent().equals(entity.getType())) {
            return this.findByType(entity.getType());
        }

        return this.findByName(entity.getParent(), entity.getType());
    }

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<TaxonomyEntity> findByParent(String parent, String type);

    @SelectProvider(type = SqlProvider.class, method = "countBy")
    int countByParent(String parent, String type);

    @UpdateProvider(type = SqlProvider.class, method = "updateBy")
    int updatePriorityById(Integer id, Integer priority);
}
