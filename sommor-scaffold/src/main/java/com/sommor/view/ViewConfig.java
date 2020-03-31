package com.sommor.view;

import com.sommor.core.utils.ClassAnnotatedTypeParser;
import com.sommor.model.Model;
import com.sommor.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
public class ViewConfig<V extends View> extends TargetConfig {

    @Getter
    @Setter
    private String name;

    @Getter
    private Class viewClass;

    public ViewConfig() {
        this.viewClass = ClassAnnotatedTypeParser.parse(this.getClass())[0];
    }

}
