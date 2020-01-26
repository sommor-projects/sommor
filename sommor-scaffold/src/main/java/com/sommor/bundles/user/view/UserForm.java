package com.sommor.bundles.user.view;

import com.sommor.bundles.media.fields.file.MediaFiles;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.fields.gender.GenderField;
import com.sommor.scaffold.view.field.EntityForm;
import com.sommor.scaffold.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/15
 */
public class UserForm extends EntityForm {

    @Getter
    @Setter
    @TextField
    private String userName;

    @Getter @Setter
    @TextField
    private String mobilePhone;

    @Getter @Setter
    @TextField
    private String email;

    @Getter @Setter
    @TextField
    private String password;

    @Getter @Setter
    @TextField
    private String nickName;

    /**
     * @see UserEntity#GENDER_MALE
     * @see UserEntity#GENDER_FEMALE
     */
    @Getter @Setter
    @GenderField
    private Integer gender;

    @Getter @Setter
    private MediaFiles avatar;
}
