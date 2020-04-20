package com.sommor.mybatis.repository;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityFieldDefinition;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.mybatis.sql.field.type.Array;
import com.sommor.mybatis.sql.select.Pagination;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/25
 */
public interface CurdRepository<Entity, ID> {

    @SelectProvider(type = SqlProvider.class, method = "findById")
    Entity findById(ID id);

    @SelectProvider(type = SqlProvider.class, method = "findFirst")
    Entity findFirst(Query query);

    @SelectProvider(type = SqlProvider.class, method = "findAll")
    List<Entity> findAll();

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<Entity> findByIds(Array id);

    default List<Entity> findByIds(Collection<ID> id) {
        return findByIds(new Array(id));
    }

    @SelectProvider(type = SqlProvider.class, method = "find")
    List<Entity> find(Query query);

    @SelectProvider(type = SqlProvider.class, method = "count")
    int count(Query query);

    default PagingResult<Entity> findByPaging(Query query) {
        PagingResult<Entity> result = new PagingResult<>();

        int totalSize = this.count(query);
        result.setTotalCount(totalSize);

        if (totalSize > 0) {
            List<Entity> entities = this.find(query);
            if (null != entities && entities.size() > 1) {
                if (entities.get(0) instanceof Comparable) {
                    Collections.sort((List<? extends Comparable>) entities);
                }
            }

            result.setData(entities);

            Pagination pagination = query.pagination();
            if (null != pagination) {
                result.setPageNo(pagination.getPage());
                result.setPageSize(pagination.getPageSize());
                result.setTotalPage((result.getTotalCount() + result.getPageSize() - 1) / result.getPageSize());

                boolean isEnd = result.getPageSize() * result.getPageNo() >= totalSize;
                result.setIsEnded(isEnd);
            } else {
                result.setPageNo(1);
                result.setTotalPage(1);
                result.setPageSize(totalSize);
                result.setIsEnded(true);
            }
        } else {
            result.setPageNo(1);
            result.setTotalPage(1);
            result.setPageSize(1);
            result.setIsEnded(true);
            result.setData(Collections.emptyList());
        }

        return result;
    }

    @InsertProvider(type = SqlProvider.class, method = "insert")
    @Options(useGeneratedKeys=true, keyColumn = "id", keyProperty = "id")
    int insertByAutoIncrement(Entity entity);

    @InsertProvider(type = SqlProvider.class, method = "insert")
    int insert(Entity entity);

    default void add(Entity entity) {
        EntityDefinition ed = SqlProvider.parseEntityDefinition(this.getClass());
        if (ed.isAutoIncrement()) {
            insertByAutoIncrement(entity);
        } else {
            insert(entity);
        }
    }

    @UpdateProvider(type = SqlProvider.class, method = "update")
    int update(Entity entity);

    @DeleteProvider(type = SqlProvider.class, method = "deleteById")
    int deleteById(ID id);


    @DeleteProvider(type = SqlProvider.class, method = "deleteBy")
    int deleteByIds(Array id);

    default int deleteByIds(Collection<ID> id) {
        return this.deleteByIds(new Array(id));
    }
}
