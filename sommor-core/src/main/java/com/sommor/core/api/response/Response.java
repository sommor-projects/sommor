package com.sommor.core.api.response;

import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.FieldError;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/1/20
 */
public class Response<Result> {

    @Getter
    @Setter
    private boolean success;

    @Getter
    @Setter
    private ErrorCode errorCode;

    @Getter
    @Setter
    private List<FieldError> errors;

    @Getter
    @Setter
    private Result result;

    public static <Result> Response<Result> success(Result result) {
        Response<Result> response = new Response<>();
        response.setSuccess(true);
        response.setResult(result);

        return response;
    }

    public static <Result> Response<Result> success() {
        return success(null);
    }

    public static <Result> Response<Result> error(Response response) {
        return error(response.errorCode, response.errors);
    }

    public static <Result> Response<Result> error(ErrorCode errorCode) {
        return error(errorCode, null);
    }

    public static <Result> Response<Result> error(ErrorCode errorCode, List<FieldError> errors) {
        Response<Result> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setErrors(errors);

        return response;
    }

    public Response<Result> addError(String field, ErrorCode errorCode) {
        if (null == errors) {
            errors = new ArrayList<>();
        }

        this.errors.add(new FieldError(field, errorCode));

        return this;
    }
}
