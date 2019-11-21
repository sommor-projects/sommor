package com.sommor.usercenter.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UserIdExistValidator.class)
public @interface UserIdExist {

    String message() default "{com.sommor.usercenter.validator.UserIdExist.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
