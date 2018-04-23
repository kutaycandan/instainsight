package com.kutaycandan.instainsight.model.response;

import com.kutaycandan.instainsight.model.IIInvoice;

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
