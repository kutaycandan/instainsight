package com.mirvinstalk.app.model.request;

import java.io.Serializable;

public class StalkAmountRequest implements Serializable {
    String VersionCode;
    String Cookie;
    String UserCode;
    String Token;
    String PurchaseKey;
    Integer StalkAmount;
    Double PurchasedMoneyAmount;
    String PurchasedMoneyCurrency;
    Integer StalkBalance;

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

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getPurchaseKey() {
        return PurchaseKey;
    }

    public void setPurchaseKey(String purchaseKey) {
        PurchaseKey = purchaseKey;
    }

    public Integer getStalkAmount() {
        return StalkAmount;
    }

    public void setStalkAmount(Integer stalkAmount) {
        StalkAmount = stalkAmount;
    }

    public Double getPurchasedMoneyAmount() {
        return PurchasedMoneyAmount;
    }

    public void setPurchasedMoneyAmount(Double purchasedMoneyAmount) {
        PurchasedMoneyAmount = purchasedMoneyAmount;
    }

    public String getPurchasedMoneyCurrency() {
        return PurchasedMoneyCurrency;
    }

    public void setPurchasedMoneyCurrency(String purchasedMoneyCurrency) {
        PurchasedMoneyCurrency = purchasedMoneyCurrency;
    }

    public Integer getStalkBalance() {
        return StalkBalance;
    }

    public void setStalkBalance(Integer stalkBalance) {
        StalkBalance = stalkBalance;
    }
}
