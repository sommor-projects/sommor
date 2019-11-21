package com.sommor.usercenter.model;

import com.sommor.usercenter.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuyu
 * @since 2019/2/5
 */
@Setter
@Getter
public class UserProfile {
    private Integer userId;

    private String nickName;

    private String avatar;

    private Integer gender;

    public static UserProfile from(UserEntity userEntity) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userEntity.getId());
        userProfile.setNickName(userEntity.getNickName());
        userProfile.setAvatar(userEntity.getAvatar());
        userProfile.setGender(userEntity.getGender());

        return userProfile;
    }

    public String getName() {
        return nickName;
    }
}
