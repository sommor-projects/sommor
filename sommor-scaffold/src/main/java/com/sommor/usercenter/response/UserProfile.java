package com.sommor.usercenter.response;

import com.sommor.usercenter.entity.UserEntity;

import java.util.List;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class UserProfile {

    private String nickname;

    private String avatar;

    private Integer gender;

    private List<String> roles;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public static UserProfile from(UserEntity userEntity) {
        UserProfile userProfile = new UserProfile();
        userProfile.setNickname(userEntity.getNickname());
        userProfile.setAvatar(userEntity.getAvatar());
        userProfile.setGender(userEntity.getGender());

        return userProfile;
    }
}
