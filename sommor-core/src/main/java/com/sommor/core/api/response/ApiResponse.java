package com.sommor.core.api.response;

import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorDetail;
import com.sommor.core.api.error.ErrorMessage;
import com.sommor.core.api.error.FieldError;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author yanguanwei@qq.com
 * @since 2019/1/20
 */
public class ApiResponse<Result> {

    private boolean success;

    private String errorCode;

    private String errorMsg;

    private List<ErrorDetail> errors;

    private Result result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public static <Result> ApiResponse<Result> success(Result result) {
        ApiResponse<Result> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setResult(result);

        return response;
    }

    public static <Result> ApiResponse<Result> success() {
        return success(null);
    }

    public static ApiResponse error(ErrorMessage errorMessage) {
        return error(errorMessage, null);
    }

    public static ApiResponse error(ErrorMessage errorMessage, List<ErrorDetail> errors) {
        ApiResponse response = new ApiResponse<>();
        response.setSuccess(false);
        response.setErrorMessage(errorMessage);
        response.setErrors(errors);

        return response;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.setErrorCode(errorMessage.getCode());
        this.setErrorMsg(errorMessage.getMessage());
    }

    public static ApiResponse error(ErrorCode errorCode) {
        return error(errorCode.toErrorMessage(LocaleContextHolder.getLocale()));
    }

    public static ApiResponse error(ErrorCode errorCode, List<ErrorDetail> errors) {
        return error(errorCode.toErrorMessage(LocaleContextHolder.getLocale()), errors);
    }

    public static <Result> ApiResponse<Result> of(Response<Result> response) {
        return of(response, LocaleContextHolder.getLocale());
    }

    public static <Result> ApiResponse<Result> of(Response<Result> response, Locale locale) {
        ApiResponse<Result> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(response.isSuccess());
        apiResponse.setResult(response.getResult());

        ErrorCode errorCode = response.getErrorCode();
        if (null != errorCode) {
            apiResponse.setErrorMessage(errorCode.toErrorMessage(locale));
        }

        List<FieldError> errors = response.getErrors();
        if (null != errors) {
            List<ErrorDetail> errorDetails = new ArrayList<>(errors.size());
            for (FieldError fieldError : errors) {
                errorDetails.add(new ErrorDetail(fieldError.getFieldName(), fieldError.getErrorCode().toErrorMessage(locale)));
            }
            apiResponse.setErrors(errorDetails);
        }

        return apiResponse;
    }
}
