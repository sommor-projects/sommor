package com.sommor.bundles.user.model;

import com.sommor.bundles.media.component.file.MediaFilesField;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.core.model.target.EntityDetail;
import com.sommor.core.model.Model;
import com.sommor.core.model.fill.OnModelFill;
import com.sommor.core.view.field.text.TextField;
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
