package com.mirvinstalk.app.model.request;

import java.io.Serializable;

public class OrderFeaturesRequest implements Serializable {
    String VersionCode;
    String UserCode;
    String Token;
    String InstaUsername;

    public String getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(String versionCode) {
        VersionCode = versionCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getInstaUsername() {
        return InstaUsername;
    }

    public void setInstaUsername(String instaUsername) {
        InstaUsername = instaUsername;
    }
}
