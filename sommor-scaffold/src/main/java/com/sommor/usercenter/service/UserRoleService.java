package com.sommor.usercenter.service;

import com.sommor.usercenter.entity.UserRoleEntity;
import com.sommor.usercenter.repository.UserRoleRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@Service
public class UserRoleService {

    @Resource
    private UserRoleRepository userRoleRepository;

    public List<String> getUserRoles(Integer userId) {
        List<UserRoleEntity> userRoleEntities = userRoleRepository.findByUserId(userId);
        List<String> roles = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userRoleEntities)) {
            for (UserRoleEntity entity : userRoleEntities) {
                roles.add(entity.getRole());
            }
        }

        return roles;
    }

    public UserRoleEntity add(UserRoleEntity userRoleEntity) {
        UserRoleEntity exist = userRoleRepository.findByUserRole(userRoleEntity.getUserId(), userRoleEntity.getRole());
        if (exist != null) {
            return exist;
        }

        userRoleRepository.add(userRoleEntity);
        return userRoleEntity;
    }

    public UserRoleEntity delete(Integer id) {
        UserRoleEntity entity = userRoleRepository.findById(id);
        userRoleRepository.deleteById(id);
        return entity;
    }
}
