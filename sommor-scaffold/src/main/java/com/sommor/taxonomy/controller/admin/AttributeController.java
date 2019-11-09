package com.sommor.taxonomy.controller.admin;

import com.sommor.api.response.ApiResponse;
import com.sommor.taxonomy.model.AttributeForm;
import com.sommor.taxonomy.service.AttributeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@RestController
public class AttributeController {

    @Resource
    private AttributeService attributeService;

    @ApiOperation(value = "添加属性")
    @RequestMapping(value = "/attribute/add", method = RequestMethod.GET)
    public String add() {
        AttributeForm form = new AttributeForm();
        return form.toHtml().toString();
    }

    @ApiOperation(value = "添加属性")
    @RequestMapping(value = "/attribute/add", method = RequestMethod.POST)
    public @ResponseBody ApiResponse add(@Validated AttributeForm attributeForm) {
        attributeService.save(attributeForm);
        return ApiResponse.success(attributeForm);
    }

    @ApiOperation(value = "更新属性")
    @RequestMapping(value = "/attribute/{id}/update", method = RequestMethod.GET)
    public String update(@PathVariable("id") Integer id) {
        AttributeForm form = attributeService.render(id);
        return form.toHtml().toString();
    }

    @ApiOperation(value = "更新属性")
    @RequestMapping(value = "/attribute/{id}/update", method = RequestMethod.POST)
    public @ResponseBody ApiResponse update(@PathVariable("id") Integer id,
                                            @Validated AttributeForm attributeForm) {
        attributeService.save(attributeForm);
        return ApiResponse.success(attributeForm);
    }
}
