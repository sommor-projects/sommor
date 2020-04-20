package com.sommor.core.component.detail;

import com.sommor.core.api.response.ApiResponse;
import com.sommor.core.utils.ClassAnnotatedTypeParser;
import com.sommor.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
public class DetailController<Entity extends BaseEntity<?>, Detail, DetailParam> {

    private DetailService<Entity, Detail, DetailParam> detailService;

    public DetailController() {
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.detailService = new DetailService<>(classes[0], classes[1]);
    }

    public DetailController(DetailService<Entity, Detail, DetailParam> detailService) {
        this.detailService = detailService;
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<Detail> renderDetail(DetailParam param) {
        Detail detail = this.detailService.renderDetail(param);
        return ApiResponse.success(detail);
    }

}
