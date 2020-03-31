package com.sommor.bundle.wine.jiukacha;

import com.sommor.api.response.ApiResponse;
import com.sommor.bundle.wine.model.WineSearchRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@RestController
@RequestMapping("/api/jiukacha")
public class JiuKaChaController {

    @Resource
    private JiuKaChaService jiuKaChaService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ApiResponse<List<JiuKaChaWineResult>> search(@RequestParam String keywords) {
       List<JiuKaChaWineResult> result = jiuKaChaService.search(WineSearchRequest.of(keywords));
       return ApiResponse.success(result);
    }
}
