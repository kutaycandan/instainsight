package com.mirvinstalk.app.model.response;

import com.mirvinstalk.app.model.IIInvoice;

import java.io.Serializable;

public class OrderFeaturesResponse implements Serializable {
    IIInvoice IIInvoice;
    int StalkBalance;

    public IIInvoice getIInvoice() {
        return IIInvoice;
    }

    public void setIInvoice(IIInvoice IInvoice) {
        this.IIInvoice = IInvoice;
    }

    public int getStalkBalance() {
        return StalkBalance;
    }

    public void setStalkBalance(int stalkBalance) {
        StalkBalance = stalkBalance;
    }
}
