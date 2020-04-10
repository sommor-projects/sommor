package com.sommor.core.view.model;

import com.sommor.core.component.form.field.FormFieldConfig;
import com.sommor.core.context.Extensible;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.Model;
import com.sommor.core.model.ModelField;
import com.sommor.core.model.ModelManager;
import com.sommor.core.model.config.TargetConfig;
import com.sommor.core.view.View;
import com.sommor.core.view.ViewConfig;
import com.sommor.core.view.ViewEngine;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.extension.ViewRenderProcessor;
import com.sommor.core.view.field.FieldsetConfig;

import java.lang.reflect.Method;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/16
 */
@Implement
public class ModelViewRenderProcessor implements ViewRenderProcessor<ModelViewConfig> {

    @Override
    public void processOnViewRender(ModelViewConfig modelViewConfig, ViewRenderContext ctx) {
        Model model = modelViewConfig.getModel();
        if (null != model) {
            ViewTree viewTree = modelViewConfig.getViewTree();
            renderModel(model, viewTree, modelViewConfig, ctx);
        }
    }

    private void renderModel(Model model, ViewTree viewTree, ModelViewConfig modelViewConfig, ViewRenderContext ctx) {
        Object target = model.getTarget();
        if (target instanceof OnViewRender) {
            ((OnViewRender) target).onViewRender(model, ctx);
        }

        Model sourceModel = ctx.getSourceMode();

        for (ModelField modelField : model.getFields()) {
            ModelFieldViewRenderContext fieldCtx = new ModelFieldViewRenderContext(ModelManager.getModelFieldSubContext(modelField));
            fieldCtx.addExt(modelViewConfig);
            // ModelField --> ViewRenderContext
            if (modelField instanceof Extensible) {
                fieldCtx.extend((Extensible) modelField);
            }

            fieldCtx.setFieldConfig(modelField.getFieldConfig());
            fieldCtx.setModel(model);
            fieldCtx.setSourceMode(sourceModel);

            ViewConfig viewConfig = parseViewConfig(modelField);
            if (null != viewConfig) {
                if (viewConfig instanceof ModelFieldConfig) {
                    ((ModelFieldConfig) viewConfig).setValue(modelField.getValue());
                }

                if (viewConfig instanceof FormFieldConfig) {
                    ((FormFieldConfig) viewConfig).setPathName(model.getPathName());
                }

                if (viewConfig instanceof FieldsetConfig) {
                    ((FieldsetConfig) viewConfig).setPathName(model.getPathName());
                }
            }

            Method method = modelField.getExt("modelFieldRenderMethod");
            if (null != target && null != method) {
                try {
                    method.invoke(target, fieldCtx);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }

            if (null != viewConfig) {
                View view = ViewEngine.render(viewConfig, sourceModel);
                viewTree.addView((view));
            }

            Model subModel = modelField.getSubModel();
            if (null != subModel) {
                ViewTree subViewTree = new ViewTree();
                subViewTree.setName(subModel.getName());
                renderModel(subModel, subViewTree, modelViewConfig, ctx);
                viewTree.addView(subViewTree);
            }
        }
    }

    private ViewConfig parseViewConfig(ModelField modelField) {
        TargetConfig targetConfig = modelField.getFieldConfig();
        if (targetConfig instanceof ViewConfig) {
            ViewConfig viewConfig = (ViewConfig) targetConfig;
            viewConfig.setName(modelField.getName());
            return viewConfig;
        }
        return null;
    }
}
