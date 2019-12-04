package com.sommor.mybatis.entity;

import com.sommor.mybatis.entity.config.Column;
import lombok.Getter;
import lombok.Setter;

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

    @Override
    public int hashCode() {
        Integer id = getId();
        if (null != id && id > 0) {
            return Objects.hash(getId());
        }
        return super.hashCode();
    }
}
