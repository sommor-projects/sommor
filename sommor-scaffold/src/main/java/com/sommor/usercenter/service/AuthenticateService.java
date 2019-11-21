package com.sommor.usercenter.service;

import com.sommor.api.response.Response;
import com.sommor.api.error.ErrorCode;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.usercenter.auth.AuthenticationHolder;
import com.sommor.usercenter.entity.AuthenticationEntity;
import com.sommor.usercenter.entity.UserEntity;
import com.sommor.usercenter.entity.UserRoleEntity;
import com.sommor.usercenter.extension.Authenticator;
import com.sommor.usercenter.jwt.Jwtoken;
import com.sommor.usercenter.jwt.JwtSession;
import com.sommor.usercenter.model.*;
import com.sommor.usercenter.repository.UserAuthRepository;
import com.sommor.usercenter.repository.UserRoleRepository;
import com.sommor.usercenter.settings.UserSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wuyu
 * @since 2019/2/3
 */
@Service
public class AuthenticateService {

    @Resource
    private UserAuthRepository userAuthRepository;

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    private Authenticator authenticator = ExtensionExecutor.proxyOf(Authenticator.class);

    public AuthUser authUser(Authentication authentication) {
        AuthUser authUser = new AuthUser();
        authUser.setAuthId(authentication.getId());
        authUser.setToken(authentication.getJwtoken().getToken());
        authUser.setExpireTime(authentication.getExpireTime());
        authUser.setUserId(authentication.getUserId());
        authUser.setRoles(getRoles(authentication.getUserId()));
        return authUser;
    }

    public LoginUser loginUser(AuthUser authUser) {
        LoginUser loginUser = new LoginUser();

        UserProfile userProfile = userService.queryUserProfile(authUser.getUserId());
        loginUser.setUserProfile(userProfile);

        loginUser.setToken(authUser.getToken());
        loginUser.setExpireTime(authUser.getExpireTime());
        loginUser.setRoles(authUser.getRoles());

        return loginUser;
    }

    private Set<String> getRoles(Integer userId) {
        List<String> roles = userRoleService.getUserRoles(userId);
        Set<String> set = new HashSet<>(roles);
        for (String role : set) {
            set.addAll(UserRoles.getInheritedRoles(role));
        }

        return set;
    }

    public <LoginParam> Response<LoginUser> login(LoginParam loginParam) {
        Response<Identity> response = authenticator.authenticate(loginParam);
        if (! response.isSuccess()) {
            return Response.error(response.getErrorCode());
        }

        Identity identity = response.getResult();
        UserEntity userEntity = identity.getUserEntity();
        Authentication authentication = saveAuthentication(userEntity, identity.getSessionKey());

        LoginUser loginUser = new LoginUser();
        loginUser.setUserProfile(UserProfile.from(userEntity));
        loginUser.setToken(authentication.getJwtoken().getToken());
        loginUser.setExpireTime(authentication.getExpireTime());
        loginUser.setRoles(getRoles(userEntity.getId()));

        return Response.success(loginUser);
    }

    private Authentication saveAuthentication(UserEntity userEntity, String sessionKey) {
        AuthenticationEntity entity = new AuthenticationEntity();
        entity.setUserId(userEntity.getId());

        int nowTime = (int) (System.currentTimeMillis() / 1000);
        entity.setAuthTime(nowTime);
        entity.setExpireTime(nowTime + UserSettings.userLoginExpireTime);
        entity.setSessionKey(sessionKey);

        Integer id = userAuthRepository.insert(entity);
        entity.setId(id);

        Authentication authentication = new Authentication();
        enrichAuthentication(authentication, entity);

        JwtSession jwtSession = new JwtSession(authentication.getId(), authentication.getExpireTime());
        Jwtoken jwtoken = new Jwtoken(jwtSession);
        authentication.setJwtoken(jwtoken);

        return authentication;
    }

    public Response<Authentication> authenticate(String token) {
        Jwtoken jwtoken = Jwtoken.from(token);
        if (null == jwtoken) {
            return Response.error(ErrorCode.of("authenticate.token.invalid"));
        }

        if (! jwtoken.verify()) {
            return Response.error(ErrorCode.of("authenticate.verify.failed"));
        }

        JwtSession jwtSession = jwtoken.getJwtSession();
        AuthenticationEntity entity = userAuthRepository.findById(jwtSession.getId());
        if (null == entity) {
            return Response.error(ErrorCode.of("authenticate.id.invalid"));
        }

        Authentication authentication = new Authentication();
        enrichAuthentication(authentication, entity);

        authentication.setJwtoken(jwtoken);

        return Response.success(authentication);
    }

    private void enrichAuthentication(Authentication authentication, AuthenticationEntity entity) {
        authentication.setId(entity.getId());
        authentication.setUserId(entity.getUserId());
        authentication.setExpireTime(entity.getExpireTime());
        authentication.setSessionKey(entity.getSessionKey());
    }

}
