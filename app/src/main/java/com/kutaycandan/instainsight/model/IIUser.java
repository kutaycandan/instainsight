package com.kutaycandan.instainsight.model;

import java.io.Serializable;
import java.util.ArrayList;

public class IIUser implements Serializable {
    String AdKey;
    String Token;
    String Cookie;
    String PurchaseKey;
    String UserCode;
    int Amount;
    String PermaCode;
    ArrayList<IIInvoice> IIInvoices;

    public String getPermaCode() {
        return PermaCode;
    }

    public void setPermaCode(String permaCode) {
        PermaCode = permaCode;
    }

    public ArrayList<IIInvoice> getIIInvoices() {
        return IIInvoices;
    }

    public void setIIInvoices(ArrayList<IIInvoice> IIInvoices) {
        this.IIInvoices = IIInvoices;
    }

    public String getAdKey() {
        return AdKey;
    }

    public void setAdKey(String adKey) {
        AdKey = adKey;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getCookie() {
        return Cookie;
    }

    public void setCookie(String cookie) {
        Cookie = cookie;
    }

    public String getPurchaseKey() {
        return PurchaseKey;
    }

    public void setPurchaseKey(String purchaseKey) {
        PurchaseKey = purchaseKey;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
