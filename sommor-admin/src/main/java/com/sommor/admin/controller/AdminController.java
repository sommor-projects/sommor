package com.sommor.admin.controller;

import com.sommor.admin.entity.TestEntity;
import com.sommor.admin.ext.def.AdminMenuRender;
import com.sommor.admin.param.PostParam;
import com.sommor.admin.repository.AdminRepository;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.mybatis.sql.select.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@RestController
public class AdminController {
    private AdminMenuRender adminMenuRender = ExtensionExecutor.proxyOf(AdminMenuRender.class);

    @Resource
    private AdminRepository adminRepository;

    @RequestMapping(value = "/admin/test", method = RequestMethod.GET)
    public PagingResult<TestEntity> test() {

        //List<TestEntity> testEntities = adminRepository.findBy(15L, 11L, "Division");

        PostParam postParam = new PostParam();
        postParam.setParentId(0L);

        postParam.pageable(2, 2);
        postParam.orderly("id", OrderType.DESC);

        PagingResult<TestEntity> pagingResult = adminRepository.findByPaging(postParam);

        return pagingResult;
    }
}
