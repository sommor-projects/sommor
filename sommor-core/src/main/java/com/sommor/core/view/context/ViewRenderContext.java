package com.sommor.core.view.context;

import com.sommor.core.context.Context;
import com.sommor.core.model.Model;
import com.sommor.core.view.View;
import com.sommor.core.view.ViewConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
public class ViewRenderContext extends Context {

    @Setter
    private View view;

    @Setter
    private ViewConfig viewConfig;

    @Getter
    @Setter
    private Model sourceMode;

    public ViewRenderContext() {
    }

    public ViewRenderContext(Context ctx) {
        super(ctx);
    }

    public <V extends View> V getView() {
        return (V) view;
    }

    public <VC extends ViewConfig> VC getViewConfig() {
        return (VC) viewConfig;
    }
}
