package com.sommor.view.context;

import com.sommor.core.context.Context;
import com.sommor.model.Model;
import com.sommor.view.View;
import com.sommor.view.ViewConfig;
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
