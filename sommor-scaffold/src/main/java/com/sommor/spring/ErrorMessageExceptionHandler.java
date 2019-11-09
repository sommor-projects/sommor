package com.sommor.spring;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@ControllerAdvice
public class ErrorMessageExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String allExceptionHandler(Exception e) {
        if(e instanceof BindException) {
            BindException ex = (BindException)e;
            BindingResult bindingResult = ex.getBindingResult();
        }
        return null;
    }

}
