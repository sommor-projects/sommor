package com.sommor.mybatis.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityFieldDefinition;
import com.sommor.mybatis.entity.definition.EntityManager;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Getter
@Setter
public class BaseEntity {

    @Column
    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        Integer id = this.getId();
        Integer thatId = that.getId();
        if (null == id || id == 0 || thatId == null || thatId == 0) {
            return false;
        }

        return Objects.equals(id, thatId);
    }

    public boolean isNew() {
        return id == null || id == 0;
    }

    @Override
    public int hashCode() {
        Integer id = getId();
        if (null != id && id > 0) {
            return Objects.hash(getId());
        }
        return super.hashCode();
    }

    public void setFieldValue(EntityFieldDefinition efd, Object value) {
        try {
            efd.getSetter().invoke(this, value);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void setFieldValue(String fieldName, Object value) {
        EntityFieldDefinition efd = this.definition().getFieldDefinition(fieldName);
        if (null != efd) {
            this.setFieldValue(efd, value);
        }
    }

    public <T> T getFieldValue(String fieldName) {
        EntityFieldDefinition efd = this.definition().getFieldDefinition(fieldName);
        if (null == efd) {
            return null;
        }

        return getFieldValue(efd);
    }

    public <T> T getFieldValue(EntityFieldDefinition efd) {
        try {
            return (T) efd.getGetter().invoke(this);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public EntityDefinition definition() {
        return EntityManager.getDefinition(this.getClass());
    }
}
