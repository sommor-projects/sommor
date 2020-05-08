package com.sommor.core.curd.delete;

import com.sommor.core.api.response.ApiResponse;
import com.sommor.core.utils.ClassAnnotatedTypeParser;
import com.sommor.core.curd.CurdManager;
import com.sommor.core.utils.Converter;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.core.scaffold.param.EntityDeleteParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/16
 */
public class EntityDeleteController<Entity extends BaseEntity> {

    private Class<Entity> entityClass;

    public EntityDeleteController() {
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.entityClass = classes[0];
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<Entity> delete(@RequestBody EntityDeleteParam param) {
        String id = param.getId();
        Entity entity = (Entity) CurdManager.getCurdService(this.entityClass).delete(id);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete-batch", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<List<Entity>> deleteBatch(@RequestBody EntityDeleteParam param) {
        List<Entity> entities = CurdManager.getCurdService(this.entityClass).deleteBatch(param.getIds());
        return ApiResponse.success(entities);
    }
}
