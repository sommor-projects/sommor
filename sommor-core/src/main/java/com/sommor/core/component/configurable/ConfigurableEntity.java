package com.sommor.core.component.configurable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.sql.field.type.Config;
import com.sommor.core.scaffold.entity.timed.TimedEntity;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
public class ConfigurableEntity<ID> extends TimedEntity<ID> {

    @Column
    @Setter
    @JsonIgnore
    private Config config;

    public Config getConfig() {
        if (null == config) {
            config = new Config();
        }
        return config;
    }

    public <T> void addConfig(String key, Object value) {
        getConfig().add(key, value);
    }

    public <T> void addConfig(T t) {
        getConfig().add(t);
    }

    public Config mergeConfig(Config config) {
        this.getConfig().merge(config);
        return this.getConfig();
    }

    @Override
    public void onSaving(Object original) {
        super.onSaving(original);

        if (null != original) {
            this.setConfig(((ConfigurableEntity) original).mergeConfig(getConfig()));
        }
    }
}
