package com.kutaycandan.instainsight.model.request;

import java.io.Serializable;

public class RegisterRequest implements Serializable {
    String VersionCode;

    public String getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(String versionCode) {
        VersionCode = versionCode;
    }
}
