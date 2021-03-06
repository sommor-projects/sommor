package com.sommor.core.scaffold.entity.timed;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.core.scaffold.entity.EntityLifecycle;
import com.sommor.core.utils.DateTimeUtil;
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
        int now = DateTimeUtil.now();
        this.setUpdateTime(now);
        if (original == null) {
            this.setCreateTime(now);
        }
    }
}
