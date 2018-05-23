package com.kutaycandan.instainsight.model.request;

import java.io.Serializable;

public class GetStalkBalanceRequest implements Serializable {
    String VersionCode;
    String Token;
    String UserCode;

    public String getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(String versionCode) {
        VersionCode = versionCode;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }
}
