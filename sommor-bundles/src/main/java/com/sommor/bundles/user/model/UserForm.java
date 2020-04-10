package com.sommor.bundles.user.model;

import com.sommor.bundles.media.component.file.MediaFiles;
import com.sommor.bundles.media.component.file.MediaFilesField;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.component.gender.GenderField;
import com.sommor.core.component.form.EntityForm;
import com.sommor.core.component.form.action.Add;
import com.sommor.core.component.form.field.InputField;
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
    @InputField(title = "用户名")
    private String userName;

    @Getter @Setter
    @InputField(title = "手机号")
    private String mobilePhone;

    @Getter @Setter
    @InputField(title = "E-Mail")
    private String email;

    @Getter @Setter
    @NotBlank(groups = {Add.class})
    @InputField(title = "密码")
    private String password;

    @Getter @Setter
    @NotBlank
    @InputField(title = "昵称")
    private String nickName;

    /**
     * @see UserEntity#GENDER_MALE
     * @see UserEntity#GENDER_FEMALE
     */
    @Getter @Setter
    @GenderField(title = "性别")
    private Integer gender;

    @Getter @Setter
    @MediaFilesField(entity = UserEntity.NAME, maxCount = 1, title = "头像")
    private MediaFiles avatar;
}
