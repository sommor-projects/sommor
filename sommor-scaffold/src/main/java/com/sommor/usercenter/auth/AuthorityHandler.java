package com.sommor.usercenter.auth;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.spring.aspect.ControllerHandler;
import com.sommor.spring.aspect.Invocation;
import com.sommor.usercenter.config.Authority;
import com.sommor.usercenter.model.AuthUser;
import com.sommor.usercenter.model.Authentication;
import com.sommor.usercenter.service.AuthenticateService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * @author wuyu
 * @since 2019/2/5
 */
@Component
public class AuthorityHandler implements ControllerHandler {

    @Resource
    private AuthenticateService authenticateService;

    @Override
    public void before(Invocation invocation) {
        Set<String> roles = new HashSet<>();

        Authority authority = invocation.getMethod().getAnnotation(Authority.class);
        if (null != authority) {
            roles.addAll(Arrays.asList(authority.roles()));
        }

        authority = invocation.getClass().getAnnotation(Authority.class);
        if (null != authority) {
            roles.addAll(Arrays.asList(authority.roles()));
        }

        if (CollectionUtils.isNotEmpty(roles)) {
            Authentication authentication = AuthenticationHolder.getAuthentication();
            if (null == authentication) {
                throw new ErrorCodeException(ErrorCode.of("authority.verify.failed"));
            }

            AuthUser authUser = authenticateService.authUser(authentication);
            if (! authUser.hasRoles(roles.toArray(new String[0]))) {
                throw new ErrorCodeException(ErrorCode.of("authority.roles.forbidden"));
            }

            AuthenticationHolder.setAuthUser(authUser);
        }
    }
}