package com.kutaycandan.instainsight.model.request;

import java.io.Serializable;

public class LoginByCookieRequest implements Serializable{
    String VersionCode;
    String Cookie;
    String UserCode;

    public String getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(String versionCode) {
        VersionCode = versionCode;
    }

    public String getCookie() {
        return Cookie;
    }

    public void setCookie(String cookie) {
        Cookie = cookie;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }
}
