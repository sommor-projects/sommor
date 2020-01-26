package com.sommor.bundles.user.auth;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.scaffold.spring.interceptor.ControllerInterceptor;
import com.sommor.scaffold.spring.interceptor.Invocation;
import com.sommor.bundles.user.config.Authority;
import com.sommor.bundles.user.model.AuthUser;
import com.sommor.bundles.user.model.Authentication;
import com.sommor.bundles.user.service.AuthenticateService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;


/**
 * @author wuyu
 * @since 2019/2/5
 */
@Component
public class AuthorityInterceptor implements ControllerInterceptor {

    @Resource
    private AuthenticateService authenticateService;

    @Override
    public void before(Invocation invocation) {
        Set<String> roles = new HashSet<>();

        List<Authority> authorities = getAuthorityFromController(invocation);
        if (CollectionUtils.isNotEmpty(authorities)) {
            for (Authority authority : authorities) {
                roles.addAll(Arrays.asList(authority.roles()));
            }
        }

        if (CollectionUtils.isNotEmpty(authorities)) {
            Authentication authentication = AuthenticationHolder.getAuthentication();
            if (null == authentication) {
                throw new ErrorCodeException(ErrorCode.of("authority.verify.failed"));
            }

            AuthUser authUser = authenticateService.authUser(authentication);
            AuthenticationHolder.setAuthUser(authUser);

            if (CollectionUtils.isNotEmpty(roles) && ! authUser.hasRoles(roles.toArray(new String[0]))) {
                throw new ErrorCodeException(ErrorCode.of("authority.roles.forbidden"));
            }
        }
    }

    private List<Authority> getAuthorityFromController(Invocation invocation) {
        List<Authority> authorities = new ArrayList<>();

        Authority authority = invocation.getMethod().getAnnotation(Authority.class);
        if (null != authority) {
            authorities.add(authority);
        }

        Class clazz = invocation.getTarget().getClass();
        while (null != clazz && clazz != Object.class) {
            authority = (Authority) clazz.getAnnotation(Authority.class);
            if (null != authority) {
                authorities.add(authority);
            }
            clazz = clazz.getSuperclass();
        }

        return authorities;
    }
}
