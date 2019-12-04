package com.sommor.view;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.view.form.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class FieldView extends View {

    @Setter
    private FormFieldDefinition definition;

    @Getter
    private FieldConstraints constraints = new FieldConstraints();

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String fullName;

    public FieldView(String type) {
        super(type);
    }

    public String getName() {
        return this.name();
    }

    public void setName(String name) {
        this.name(name);
    }

    public void renderForm(EntityForm form) {
        if (null != definition) {
            Annotation viewConfig = definition.getConfig();
            ExtensionExecutor.of(FieldConfigProcessor.class)
                    .run(viewConfig,
                            ext -> ext.processOnFormRender(viewConfig, this, form)
                    );
        }

        form.renderField(this);

        ExtensionExecutor.of(FieldViewProcessor.class)
                .run(this,
                        ext -> ext.processOnFormRender(this, form)
                );

        this.doRenderForm(form, this.definition);
    }

    protected void doRenderForm(EntityForm form, FormFieldDefinition formFieldDefinition) {
    }
}
