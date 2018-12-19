package com.mirvinstalk.app.model.request;

import java.io.Serializable;

public class GetFeatureDataRequest implements Serializable {
    String Token;
    String IIFeatureOrderGuid;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getIIFeatureOrderGuid() {
        return IIFeatureOrderGuid;
    }

    public void setIIFeatureOrderGuid(String IIFeatureOrderGuid) {
        this.IIFeatureOrderGuid = IIFeatureOrderGuid;
    }
}
