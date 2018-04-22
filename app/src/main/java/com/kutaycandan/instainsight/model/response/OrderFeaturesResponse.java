package com.kutaycandan.instainsight.model.response;

import com.kutaycandan.instainsight.model.IIInvoice;

import java.io.Serializable;

public class OrderFeaturesResponse implements Serializable {
    IIInvoice IInvoice;
    int StalkBalance;

    public IIInvoice getIInvoice() {
        return IInvoice;
    }

    public void setIInvoice(IIInvoice IInvoice) {
        this.IInvoice = IInvoice;
    }

    public int getStalkBalance() {
        return StalkBalance;
    }

    public void setStalkBalance(int stalkBalance) {
        StalkBalance = stalkBalance;
    }
}
