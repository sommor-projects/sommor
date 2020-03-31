package com.sommor.component.entity.select;

import com.sommor.api.response.ApiResponse;
import com.sommor.core.utils.ClassAnnotatedTypeParser;
import com.sommor.component.form.field.Option;
import com.sommor.curd.CurdManager;
import com.sommor.curd.CurdService;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.query.PagingResult;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/2
 */
abstract public class EntitySelectController<Entity extends BaseEntity, EntityQueryParam> implements EntitySelectOptionParser<Entity> {

    private Class entityClass;

    public EntitySelectController() {
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.entityClass = classes[0];
    }

    @ApiOperation(value = "选项")
    @RequestMapping(value = "/select/options", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<List<Option>> renderSelect(EntityQueryParam entityQueryParam) {
        CurdService curdService = CurdManager.getCurdService(this.entityClass);
        PagingResult<Entity> pagingResult =  curdService.queryByPaging(entityQueryParam);

        List<Option> options = new ArrayList<>();

        for (Entity entity : pagingResult.getData()) {
            options.add(this.parseEntitySelectOption(entity));
        }

        return ApiResponse.success(options);
    }

    public Option parseEntitySelectOption(Entity entity) {
        String title = entity.getFieldValue("title");
        String subTitle = entity.getFieldValue("subTitle");

        String label;
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(subTitle)) {
            label = title + "("+ subTitle + ")";
        } else if (StringUtils.isNotBlank(title)) {
            label = title;
        } else if (StringUtils.isNotBlank(subTitle)) {
            label = subTitle;
        } else {
            label = entity.getId().toString();
        }

        return new Option(label, entity.getId());
    }
}
