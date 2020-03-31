package com.sommor.bundle.user.model;

import com.sommor.bundle.media.component.file.MediaFilesField;
import com.sommor.bundle.user.entity.UserEntity;
import com.sommor.model.target.EntityDetail;
import com.sommor.model.Model;
import com.sommor.model.fill.OnModelFill;
import com.sommor.view.field.text.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class UserProfile extends EntityDetail implements OnModelFill {

    @Setter @Getter
    @TextField
    private Integer userId;


    @Setter @Getter
    @TextField
    private String userName;

    @Setter @Getter
    @TextField
    private String nickName;

    @Setter @Getter
    @MediaFilesField(entity = UserEntity.NAME)
    private String avatar;

    @Setter @Getter
    @TextField
    private Integer gender;


    public String getName() {
        return nickName;
    }

    @Override
    public void onModelFill(Model model, Model sourceModel) {
        this.setUserId(this.getId());
    }
}
