package com.sommor.usercenter.auth;

import com.sommor.api.response.Response;
import com.sommor.usercenter.model.Authentication;
import com.sommor.usercenter.service.AuthenticateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@ControllerAdvice
public class AuthenticationResolver {

    @Resource
    private AuthenticateService authenticateService;

    @ModelAttribute
    public void authentication(
            @RequestHeader(required = false, name = "X-Authentication") String token,
            Model model,
            HttpServletRequest request) {
        if (StringUtils.isNotBlank(token)) {
            Response<Authentication> response = authenticateService.authenticate(token);
            if (response.isSuccess()) {
                Authentication authentication = response.getResult();
                model.addAttribute("authentication", authentication);
                request.setAttribute("authentication", authentication);
                AuthenticationHolder.setAuthentication(authentication);
            }
        }
    }
}
