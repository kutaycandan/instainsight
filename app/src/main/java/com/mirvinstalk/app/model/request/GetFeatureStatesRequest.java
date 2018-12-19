package com.mirvinstalk.app.model.request;

import java.util.ArrayList;

public class GetFeatureStatesRequest {
    String Token;
    ArrayList<String> IIFeatureOrderGuids;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public ArrayList<String> getIIFeatureOrderGuids() {
        return IIFeatureOrderGuids;
    }

    public void setIIFeatureOrderGuids(ArrayList<String> IIFeatureOrderGuids) {
        this.IIFeatureOrderGuids = IIFeatureOrderGuids;
    }
}
