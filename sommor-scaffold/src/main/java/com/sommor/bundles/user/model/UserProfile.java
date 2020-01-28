package com.sommor.bundles.user.model;

import com.sommor.bundles.media.fields.file.MediaFilesField;
import com.sommor.scaffold.view.field.DataSource;
import com.sommor.scaffold.view.field.EntityDetail;
import com.sommor.scaffold.view.field.config.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class UserProfile extends EntityDetail {

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
    @MediaFilesField
    private String avatar;

    @Setter @Getter
    @TextField
    private Integer gender;


    @Override
    public void onFill(DataSource targetData) {
        super.onFill(targetData);
        this.setUserId(this.getId());
    }

    public String getName() {
        return nickName;
    }
}
