package com.sommor.bundles.mall.shop.controller;

import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.search.entity.SubjectIndex;
import com.sommor.bundles.mall.shop.repository.ShopRepository;
import com.sommor.core.api.response.ApiResponse;
import com.sommor.core.model.Model;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
@RestController
@RequestMapping(value = "/api/shop")
public class ShopIndexController {

    @Resource
    private ShopRepository shopRepository;

    @ApiOperation(value = "构建索引")
    @RequestMapping(value = "/index/build", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<List<SubjectIndex>> buildIndex() {
        List<SubjectIndex> shopIndices = new ArrayList<>();
        for (ShopEntity shopEntity : shopRepository.findAll()) {
            SubjectIndex subjectIndex = new SubjectIndex();
            Model.of(subjectIndex).fill(Model.of(shopEntity));
            shopIndices.add(subjectIndex);
        }

        return ApiResponse.success(shopIndices);
    }
}
