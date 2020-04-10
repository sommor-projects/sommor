package com.sommor.core.view;

import com.sommor.core.context.RequestContext;
import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.core.model.Model;
import com.sommor.core.model.ModelManager;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.extension.ViewRenderProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
public class ViewEngine {

    private static final ExtensionExecutor<ViewRenderProcessor> viewRenderProcessor = ExtensionExecutor.of(ViewRenderProcessor.class);

    public static <V extends View, VC extends ViewConfig<V>> V render(VC viewConfig) {
        return render(viewConfig, new Model());
    }

    public static <V extends View, VC extends ViewConfig<V>> V render(VC viewConfig, Model sourceModel) {
        V view = parseView(viewConfig);

        ModelManager.setModelFieldValues(view, viewConfig);

        renderFromExtension(view, viewConfig, sourceModel);

        return view;
    }

    @SuppressWarnings("unchecked")
    private static <V extends View, VC extends ViewConfig<V>> V parseView(VC viewConfig) {
        V view;

        try {
            view = (V) viewConfig.getViewClass().newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        view.setName(viewConfig.getName());

        return view;
    }

    private static void renderFromExtension(View view, ViewConfig viewConfig, Model sourceModel) {
        ViewRenderContext ctx = new ViewRenderContext(RequestContext.getSubContext(viewConfig));
        ctx.setView(view);
        ctx.setViewConfig(viewConfig);
        ctx.setSourceMode(sourceModel);

        renderFromExtension(viewConfig.getClass(), viewConfig, ctx);
    }

    private static void renderFromExtension(Class viewConfigClass, ViewConfig viewConfig, ViewRenderContext ctx) {
        viewRenderProcessor.run(viewConfigClass, ext -> ext.processOnViewRender(viewConfig, ctx));

        Class parentClass = viewConfigClass.getSuperclass();
        if (parentClass != null && parentClass != Object.class) {
            renderFromExtension(parentClass, viewConfig, ctx);
        }
    }
}
