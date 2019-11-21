package com.sommor.usercenter.jwt;

import com.alibaba.fastjson.JSON;
import com.sommor.usercenter.auth.Encryption;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author wuyu
 * @since 2019/2/1
 */
public class Jwtoken {

    private static final String SECRET_KEY = "123456";

    private JwtSession jwtSession;

    private String encodedAuthSession;

    private String signature;

    private String token;

    private Jwtoken() {

    }

    public Jwtoken(JwtSession jwtSession) {
        this.jwtSession = jwtSession;

        this.encodedAuthSession = Base64.getEncoder().encodeToString(
            JSON.toJSONString(jwtSession).getBytes(StandardCharsets.UTF_8)
        );

        this.signature = generateSignature(this.encodedAuthSession);

        this.token = generateToken(this.encodedAuthSession, signature);
    }

    public JwtSession getJwtSession() {
        return jwtSession;
    }

    public String getSignature() {
        return signature;
    }

    public String getToken() {
        return token;
    }

    public boolean isExpired(long now) {
        return this.jwtSession.getExpiredTime() < (now / 1000);
    }

    public boolean verifySignature() {
        String signature = generateSignature(this.encodedAuthSession);
        return signature.equals(this.signature);
    }

    public boolean verify() {
        return verifySignature() && ! isExpired(System.currentTimeMillis());
    }

    private static String generateSignature(String encodedAuthSession) {
        return Encryption.hmacsha256(encodedAuthSession, SECRET_KEY);
    }

    private static String generateToken(String encodedAuthSession, String signature) {
        return encodedAuthSession + "." + signature;
    }

    public static Jwtoken from(JwtSession jwtSession) {
        return new Jwtoken(jwtSession);
    }

    public static Jwtoken from(String token) {
        String[] s = token.split("\\.");

        if (s.length != 2) {
            return null;
        }

        String encodedAuthSession = s[0];
        String signature = s[1];

        JwtSession jwtSession = JSON.parseObject(
            new String(Base64.getDecoder().decode(encodedAuthSession), StandardCharsets.UTF_8),
            JwtSession.class
        );

        Jwtoken jwtoken = new Jwtoken();
        jwtoken.jwtSession = jwtSession;
        jwtoken.encodedAuthSession = encodedAuthSession;
        jwtoken.signature = signature;
        jwtoken.token = token;

        return jwtoken;
    }
}
