package com.kutaycandan.instainsight.model.response;

import com.kutaycandan.instainsight.model.IIUser;

import java.io.Serializable;

public class RegisterResponse implements Serializable {
    IIUser IIUser;

    public com.kutaycandan.instainsight.model.IIUser getIIUser() {
        return IIUser;
    }

    public void setIIUser(com.kutaycandan.instainsight.model.IIUser IIUser) {
        this.IIUser = IIUser;
    }
}
