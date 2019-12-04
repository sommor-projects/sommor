package com.sommor.scaffold.spring;

import com.sommor.api.error.*;
import com.sommor.api.response.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@ControllerAdvice
public class ErrorMessageExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResponse allExceptionHandler(Exception e) {
        e.printStackTrace();
        ErrorMessage errorMessage = null;

        if(e instanceof MethodArgumentNotValidException) {
            List<ErrorDetail> errorDetails = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().stream()
                    .map(p-> new ErrorDetail(p.getField(), p.getCode(), p.getDefaultMessage()))
                    .collect(Collectors.toList());
            ErrorCode errorCode = ErrorCode.of(errorDetails.get(0).getErrorCode());
            return ApiResponse.error(errorCode, errorDetails);
        } else if (e instanceof ErrorMessageException) {
            errorMessage = ((ErrorMessageException) e).getErrorMessage();
        } else if (e instanceof ErrorCodeException) {
            errorMessage = ((ErrorCodeException) e).getErrorCode().toErrorMessage();
        } else {
            errorMessage = ErrorMessage.of("unknown");
        }

        return ApiResponse.error(errorMessage);
    }

}
