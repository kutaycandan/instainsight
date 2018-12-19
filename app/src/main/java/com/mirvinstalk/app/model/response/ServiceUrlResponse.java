package com.mirvinstalk.app.model.response;

import java.io.Serializable;

public class ServiceUrlResponse implements Serializable {
    private String ServiceUrl;

    public String getServiceUrl() {
        return ServiceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        ServiceUrl = serviceUrl;
    }
}
