package com.sommor.bundles.user.service;

import com.sommor.bundles.user.view.UserQueryParam;
import com.sommor.bundles.user.view.UserDetail;
import com.sommor.bundles.user.view.UserTable;
import com.sommor.scaffold.view.Option;
import com.sommor.scaffold.view.field.Form;
import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.scaffold.service.CurdService;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.view.UserForm;
import com.sommor.bundles.user.model.UserProfile;
import com.sommor.bundles.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-15
 */
@Service
public class UserService extends CurdService<
        UserEntity,
        UserForm,
        EntityFormRenderParam,
        UserDetail,
        EntityDetailParam,
        UserTable,
        UserQueryParam> {

    @Resource
    private UserRepository userRepository;

    public UserProfile queryUserProfile(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId);

        UserProfile userProfile = new UserProfile();
        userProfile.fill(userEntity);

        return userProfile;
    }

    @Override
    protected void onFormSaving(Form form, UserEntity entity, UserEntity originalEntity) {
        super.onFormSaving(form, entity, originalEntity);

        if (null == originalEntity) {
            originalEntity.setStatus(UserEntity.STATUS_NORMAL);
        }
    }

    @Override
    public Option convertSelectOption(UserEntity entity) {
        String label = entity.getUserName() + "(" + entity.getId() + ", " + entity.getNickName()+")";
        return new Option(label, entity.getId());
    }
}
