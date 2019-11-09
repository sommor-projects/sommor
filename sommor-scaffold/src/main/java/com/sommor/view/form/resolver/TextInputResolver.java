package com.sommor.view.form.resolver;

import com.sommor.extensibility.config.Implement;
import com.sommor.view.FieldView;
import com.sommor.view.FormView;
import com.sommor.view.config.TextInput;
import com.sommor.view.form.FormField;
import com.sommor.view.form.FormFieldDefinition;
import com.sommor.view.form.FormFieldResolver;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Implement
public class TextInputResolver implements FormFieldResolver<TextInput> {

    @Override
    public void resolveOnInit(TextInput textInput, FormFieldDefinition definition) {
        Field field = definition.getField();

        NotBlank notBlank = field.getAnnotation(NotBlank.class);
        if (null != notBlank) {
            definition.getConstraints().required();
        }

        Size size = field.getAnnotation(Size.class);
        if (null != size) {
            if (size.max() > 0) {
                definition.getConstraints().maxLength(size.max());
            }
        }
    }

    @Override
    public void resolveOnRender(TextInput textInput, FieldView view, FormView formView, FormField formField) {

    }
}
