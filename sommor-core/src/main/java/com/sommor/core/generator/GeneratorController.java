package com.sommor.core.generator;

import com.sommor.core.api.response.ApiResponse;
import com.sommor.core.spring.ApplicationContextHolder;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
@RestController
@RequestMapping(value = "/api/id")
public class GeneratorController {

    private static final String[] ID_NAMES = {
            "userId", "shopId", "productId", "orderId"
    };

    @ApiOperation(value = "ID生成器")
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<List<String>> fixProductId(String idName, int count) {
        List<String> list = new ArrayList<>();

        String[] idNames;
        if (StringUtils.isBlank(idName)) {
            idNames = ID_NAMES;
        } else {
            idNames = new String[] { idName };
        }

        for (String name : idNames) {
            IdGenerator idGenerator = ApplicationContextHolder.getBean(name + "Generator");
            render(name, idGenerator, list, count);
            list.add(" ");
        }

        return ApiResponse.success(list);
    }

    private void render(String idName, IdGenerator idGenerator, List<String> list, int count) {
        list.add(idName);
        list.add(renderGeneratorRange(idGenerator));

        list.add("");
        for (int i=0; i<count; i++) {
            long id = idGenerator.generateId();
            list.add(Long.toBinaryString(id) + " : " + id);
        }
    }

    private String renderGeneratorRange(IdGenerator idGenerator) {
        int length = idGenerator.getLength();
        long min = 1 << (length-1);
        long max = (1 << length) -1;

        return Long.toBinaryString(min) + "("+(length-1)+") ~ " + Long.toBinaryString(max) + "("+length+") : " + "["+min+"("+String.valueOf(min).length()+") ~ "+max+"("+String.valueOf(max).length()+")]";
    }
}
