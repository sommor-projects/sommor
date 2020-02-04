package com.sommor.bundles.user.view;

import com.sommor.bundles.media.fields.file.MediaFiles;
import com.sommor.bundles.media.fields.file.MediaFilesField;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.fields.gender.GenderField;
import com.sommor.core.view.field.EntityForm;
import com.sommor.core.view.field.action.Add;
import com.sommor.core.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/15
 */
public class UserForm extends EntityForm {

    @Getter
    @Setter
    @NotBlank
    @TextField(title = "用户名")
    private String userName;

    @Getter @Setter
    @TextField(title = "手机号")
    private String mobilePhone;

    @Getter @Setter
    @TextField(title = "E-Mail")
    private String email;

    @Getter @Setter
    @NotBlank(groups = {Add.class})
    @TextField(title = "密码")
    private String password;

    @Getter @Setter
    @NotBlank
    @TextField(title = "昵称")
    private String nickName;

    /**
     * @see UserEntity#GENDER_MALE
     * @see UserEntity#GENDER_FEMALE
     */
    @Getter @Setter
    @GenderField(title = "性别")
    private Integer gender;

    @Getter @Setter
    @MediaFilesField(maxCount = 1, title = "头像")
    private MediaFiles avatar;
}
