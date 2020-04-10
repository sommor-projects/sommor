package com.sommor.core.view.model;

import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.FieldDefinition;
import com.sommor.core.model.define.FieldDefineInterceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/28
 */
@Implement
public class ModelFieldDefineInterceptor implements FieldDefineInterceptor {

    @Override
    public void interceptOnFieldDefine(FieldDefinition fieldDefinition) {
        Field field = fieldDefinition.getField();
        Class clazz = field.getDeclaringClass();
        String fieldName = field.getName();
        String renderMethodName = "render" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        Method method = null;
        try {
            method = clazz.getMethod(renderMethodName, new Class[] { ModelFieldViewRenderContext.class });
            method.setAccessible(true);
            fieldDefinition.addExt("modelFieldRenderMethod", method);
        } catch (NoSuchMethodException e) {
            // ignore
        }
    }
}
