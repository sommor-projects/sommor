package com.sommor.core.component.form.field;

import com.sommor.core.component.form.Constraint;
import com.sommor.core.component.form.FormViewConfig;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.FieldDefinition;
import com.sommor.core.model.define.FieldConfigDefineProcessor;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.extension.ViewRenderProcessor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/16
 */
@Implement
public class FormFieldRenderProcessor implements
        FieldConfigDefineProcessor<FormFieldConfig>,
        ViewRenderProcessor<FormFieldConfig> {

    private static final Class[] defaultGroups = new Class[] { Default.class };

    @Override
    public void processOnFieldConfigDefine(FieldDefinition fieldDefinition) {
        FormFieldConstraints constraints = parseConstraints(fieldDefinition);
        fieldDefinition.addExt(constraints);
    }

    private FormFieldConstraints parseConstraints(FieldDefinition fieldDefinition) {
        FormFieldConstraints constraints = new FormFieldConstraints();

        NotBlank notBlank = fieldDefinition.getAnnotation(NotBlank.class);
        if (null != notBlank) {
            Class[] groups = notBlank.groups();
            if (groups.length == 0) {
                groups = defaultGroups;
            }
            for (Class group : groups) {
                constraints.getConstraint(group).required();
            }
        }

        NotNull notNull = fieldDefinition.getAnnotation(NotNull.class);
        if (null != notNull) {
            Class[] groups = notNull.groups();
            if (groups.length == 0) {
                groups = defaultGroups;
            }
            for (Class group : groups) {
                constraints.getConstraint(group).required();
            }
        }

        Size size = fieldDefinition.getAnnotation(Size.class);
        if (null != size) {
            Class[] groups = notNull.groups();
            if (groups.length == 0) {
                groups = defaultGroups;
            }

            for (Class group : groups) {
                Constraint fc = constraints.getConstraint(group);
                if (size.min() > 0) {
                    fc.minLength(size.min());
                }
                if (size.max() > 0) {
                    fc.maxLength(size.max());
                }
            }
        }

        return constraints;
    }

    @Override
    public void processOnViewRender(FormFieldConfig formFieldConfig, ViewRenderContext ctx) {
        FormFieldView view = ctx.getView();

        processViewFullName(view, formFieldConfig);
        processViewConstraint(view, formFieldConfig, ctx);
    }

    private void processViewFullName(FormFieldView view, FormFieldConfig formFieldConfig) {
        String fullName = view.getName();

        String pathName = formFieldConfig.getPathName();
        if (StringUtils.isNotBlank(pathName)) {
            fullName = pathName + "." + fullName;
        }

        view.setFullName(fullName);
    }

    private void processViewConstraint(FormFieldView view, FormFieldConfig formFieldConfig, ViewRenderContext ctx) {
        Constraint constraint = new Constraint();

        FormViewConfig formViewConfig = ctx.getExt(FormViewConfig.class);
        if (null != formViewConfig) {
            FormFieldConstraints constraints = ctx.getExt(FormFieldConstraints.class);
            if (null != constraints) {
                mergeFormFieldConstraint(constraint, constraints, formViewConfig.getFormAction().actionClass());
            }
        }

        if (null != formFieldConfig.getConstraint()) {
            constraint.merge(formFieldConfig.getConstraint());
        }

        view.setConstraint(constraint);
    }

    private void mergeFormFieldConstraint(Constraint constraint, FormFieldConstraints constraints, Class formActionClass) {
        Class parentClass = formActionClass.getSuperclass();
        if (null != parentClass && parentClass != Object.class) {
            mergeFormFieldConstraint(constraint, constraints, parentClass);
        }
        Class[] parentClasses = formActionClass.getInterfaces();
        if (null != parentClasses) {
            for (Class pc : parentClasses) {
                mergeFormFieldConstraint(constraint, constraints, pc);
            }
        }

        Constraint c = constraints.getConstraint(formActionClass);
        if (null != c) {
            constraint.merge(c);
        }
    }
}
