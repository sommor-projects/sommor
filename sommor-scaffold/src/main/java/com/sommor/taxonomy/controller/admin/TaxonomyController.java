package com.sommor.taxonomy.controller.admin;

import com.sommor.taxonomy.model.TaxonomyForm;
import com.sommor.view.html.HtmlElement;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@RestController
public class TaxonomyController {

    @ApiOperation(value = "添加分类")
    @RequestMapping(value = "/taxonomy/add", method = RequestMethod.GET)
    public String form() {
        TaxonomyForm form = new TaxonomyForm();
        return form.toHtml().toString();
    }
}
