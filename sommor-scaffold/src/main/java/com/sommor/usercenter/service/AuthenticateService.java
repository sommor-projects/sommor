package com.sommor.usercenter.service;

import com.sommor.api.response.Response;
import com.sommor.api.error.ErrorCode;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.usercenter.entity.AuthenticationEntity;
import com.sommor.usercenter.entity.UserEntity;
import com.sommor.usercenter.extension.Authenticator;
import com.sommor.usercenter.jwt.Jwtoken;
import com.sommor.usercenter.jwt.JwtSession;
import com.sommor.usercenter.model.Authentication;
import com.sommor.usercenter.model.Identity;
import com.sommor.usercenter.response.LoginUser;
import com.sommor.usercenter.response.UserProfile;
import com.sommor.usercenter.repository.UserAuthRepository;
import com.sommor.usercenter.settings.UserSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuyu
 * @since 2019/2/3
 */
@Service
public class AuthenticateService {

    @Resource
    private UserAuthRepository userAuthRepository;

    private Authenticator authenticator = ExtensionExecutor.proxyOf(Authenticator.class);

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

        return Response.success(loginUser);
    }

    private Authentication saveAuthentication(UserEntity userEntity, String sessionKey) {
        AuthenticationEntity entity = new AuthenticationEntity();
        entity.setUserId(userEntity.getId());

        Long nowTime = System.currentTimeMillis();
        entity.setAuthTime(nowTime);
        entity.setExpireTime(nowTime + UserSettings.userLoginExpireTime);
        entity.setSessionKey(sessionKey);

        userAuthRepository.insert(entity);

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
