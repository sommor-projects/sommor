package com.sommor.mybatis.repository;

import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.FieldDefinition;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.sql.SqlProvider;
import com.sommor.mybatis.sql.select.Pagination;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/25
 */
public interface CurdRepository<Entity> {

    @SelectProvider(type = SqlProvider.class, method = "findById")
    Entity findById(Object id);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    List<Entity> findByIds(List<Integer> id);

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
        }

        return result;
    }

    @InsertProvider(type = SqlProvider.class, method = "insert")
    @Options(useGeneratedKeys=true, keyColumn = "id", keyProperty = "id")
    Integer insert(Entity entity);

    default void add(Entity entity) {
        EntityDefinition ed = SqlProvider.parseEntityDefinition(this.getClass());
        FieldDefinition primaryField = ed.getPrimaryField();
        Object id = insert(entity);
        try {
            primaryField.getFieldSetMethod().invoke(entity, id);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @UpdateProvider(type = SqlProvider.class, method = "update")
    Integer update(Entity entity);

    default void save(Entity entity) {
        EntityDefinition ed = SqlProvider.parseEntityDefinition(this.getClass());

        FieldDefinition primaryField = ed.getPrimaryField();
        Object primaryValue = SqlProvider.getEntityFieldValue(primaryField, entity);
        if (null == primaryValue || (primaryValue instanceof Integer && ((int) primaryValue) == 0)) {
            insert(entity);
        } else {
            update(entity);
        }
    }

    @SelectProvider(type = SqlProvider.class, method = "deleteById")
    Integer deleteById(Object id);
}
