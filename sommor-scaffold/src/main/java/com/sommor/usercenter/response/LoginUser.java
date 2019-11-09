package com.sommor.usercenter.response;

/**
 * @author wuyu
 * @since 2019/2/3
 */
public class LoginUser {

    private String token;

    private UserProfile userProfile;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return this.token;
    }
}
