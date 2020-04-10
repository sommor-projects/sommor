package com.sommor.bundles.user.controller;

import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.model.UserQueryParam;
import com.sommor.core.component.entity.select.EntitySelectController;
import com.sommor.core.component.form.field.Option;
import com.sommor.extensibility.config.Implement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/16
 */
@Implement
@RestController
@RequestMapping("/api/user")
public class UserSelectController extends EntitySelectController<UserEntity, UserQueryParam> {

    @Override
    public Option parseEntitySelectOption(UserEntity entity) {
        String label = entity.getUserName() + "(" + entity.getId() + ", " + entity.getNickName()+")";
        return new Option(label, entity.getId());
    }
}
