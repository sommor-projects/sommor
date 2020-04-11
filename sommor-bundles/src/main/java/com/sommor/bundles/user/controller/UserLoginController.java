package com.sommor.bundles.user.controller;

import com.sommor.core.api.response.ApiResponse;
import com.sommor.core.api.response.Response;
import com.sommor.bundles.user.auth.AuthenticationHolder;
import com.sommor.bundles.user.config.Authority;
import com.sommor.bundles.user.model.*;
import com.sommor.bundles.user.service.AuthenticateService;
import com.sommor.bundles.user.service.UserService;
import com.sommor.core.generator.IdGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
@RestController
@Api("用户登录")
@RequestMapping("/api")
public class UserLoginController {

    @Autowired
    private AuthenticateService authenticateService;

    @Resource
    private UserService userService;

    @Resource
    private IdGenerator userIdGenerator;

    @ApiOperation(value = "用户ID生成")
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ApiResponse<Set<String>> idGenerate(Integer count) throws InterruptedException {
        AtomicInteger i = new AtomicInteger(0);
        Set<String> list = new ConcurrentSkipListSet<>();

        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int j=0; j<threadCount; j++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (i.getAndAdd(1) < count) {
                        long id = userIdGenerator.generateId();
                        list.add(Long.toBinaryString(id) + " : " + String.valueOf(id));
                    }
                    latch.countDown();
                }
            });
        }

        latch.await();
        return ApiResponse.success(new TreeSet<>(list));
    }

    @ApiOperation(value = "用户账号登录")
    @RequestMapping(value = "/login/account", method = RequestMethod.POST)
    public ApiResponse<LoginUser> login(@RequestBody @Validated UserLoginParam userLoginParam) {
        Response<LoginUser> response = authenticateService.login(userLoginParam);
        return ApiResponse.of(response);
    }

    @ApiOperation(value = "用户账号登录")
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ApiResponse<LoginUser> authLogin(@RequestBody @Validated UserLoginParam userLoginParam) {
        return this.login(userLoginParam);
    }

    @ApiOperation(value = "用户登录信息")
    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    @Authority(roles = {UserRoles.USER})
    public ApiResponse<LoginUser> queryLoginUser() {
        LoginUser loginUser = authenticateService.loginUser(AuthenticationHolder.getAuthUser());
        return ApiResponse.success(loginUser);
    }

    @ApiOperation(value = "用户账号登录")
    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    @Authority(roles = {UserRoles.USER})
    public ApiResponse<UserProfile> queryUserProfile() {
        UserProfile userProfile = userService.queryUserProfile(AuthenticationHolder.getAuthUser().getUserId());
        return ApiResponse.success(userProfile);
    }

    @ApiOperation(value = "用户账号退出")
    @RequestMapping(value = "/auth/logout", method = RequestMethod.POST)
    public ApiResponse logout() {
        Authentication authentication = AuthenticationHolder.getAuthentication();
        if (null != authentication) {
            authenticateService.logout(authentication);
        }
        return ApiResponse.success();
    }

    @ApiOperation(value = "用户登录验证")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ApiResponse<Authentication> login(String token) {
        Response<Authentication> response = authenticateService.authenticate(token);
        return ApiResponse.of(response);
    }
}
