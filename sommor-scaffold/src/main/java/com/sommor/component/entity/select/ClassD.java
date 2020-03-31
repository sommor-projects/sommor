package com.sommor.component.entity.select;

import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.user.entity.UserEntity;

import java.lang.reflect.AnnotatedType;

/**
 * @author yanguanwei@qq.com
 * @since 2020/3/28
 */
public class ClassD extends ClassC<UserEntity, TaxonomyEntity> {

    public static void main(String[] args) {
        AnnotatedType annotatedType = ClassD.class.getAnnotatedSuperclass();
        System.out.println(annotatedType);
    }
}
