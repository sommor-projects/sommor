package com.sommor.usercenter.auth;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.usercenter.model.AuthUser;
import com.sommor.usercenter.model.Authentication;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
public class AuthenticationHolder {

    public static void setAuthentication(Authentication authentication) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            requestAttributes.setAttribute("authentication", authentication, SCOPE_REQUEST);
        }
    }

    public static Authentication getAuthentication() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            return (Authentication) requestAttributes.getAttribute("authentication", SCOPE_REQUEST);
        }
        throw new ErrorCodeException(ErrorCode.of("user.unautherized"));
    }

    public static void setAuthUser(AuthUser authUser) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            requestAttributes.setAttribute("authUser", authUser, SCOPE_REQUEST);
        }
    }

    public static AuthUser getAuthUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            return (AuthUser) requestAttributes.getAttribute("authUser", SCOPE_REQUEST);
        }
        throw new ErrorCodeException(ErrorCode.of("user.unauthorized"));
    }
}
