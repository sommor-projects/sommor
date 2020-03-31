package com.sommor.view.field;

import com.sommor.view.ViewConfig;
import com.sommor.view.model.ViewTree;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
public class FieldsetConfig<FV extends FieldsetView> extends ViewConfig<FV> {

    @Getter
    @Setter
    private String pathName;

    @Getter
    private ViewTree viewTree = new ViewTree();

}
