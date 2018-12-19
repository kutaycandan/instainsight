package com.mirvinstalk.app.model.response;

import com.mirvinstalk.app.model.IIUser;

import java.io.Serializable;

public class ActivePurchasesResponse implements Serializable {
    IIUser IIUser;

    public IIUser getIIUser() {
        return IIUser;
    }

    public void setIIUser(IIUser IIUser) {
        this.IIUser = IIUser;
    }
}
