package com.kutaycandan.instainsight.model.response;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable{
    boolean Success;
    String UserMessage;
    String ExceptionMessage;
    T Data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getUserMessage() {
        return UserMessage;
    }

    public void setUserMessage(String userMessage) {
        UserMessage = userMessage;
    }

    public String getExceptionMessage() {
        return ExceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        ExceptionMessage = exceptionMessage;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
