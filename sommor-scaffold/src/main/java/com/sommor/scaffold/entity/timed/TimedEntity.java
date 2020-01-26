package com.sommor.scaffold.entity.timed;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.scaffold.entity.EntityLifecycle;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Getter
@Setter
public class TimedEntity extends BaseEntity implements EntityLifecycle {

    @Column
    private Integer updateTime;

    @Column
    private Integer createTime;

    @Override
    public void onSaving(BaseEntity original) {
        int now = (int) (System.currentTimeMillis() / 1000);
        this.setUpdateTime(now);
        if (original == null) {
            this.setCreateTime(now);
        }
    }
}
