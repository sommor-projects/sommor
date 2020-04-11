package com.sommor.bundles.user.validator;

import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.repository.UserRepository;
import com.sommor.core.utils.Converter;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
public class UserIdExistValidator implements ConstraintValidator<UserIdExist, Object> {

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserEntity userEntity = userRepository.findById(Converter.parseLong(value));
        return null != userEntity;
    }
}
