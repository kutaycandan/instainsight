package com.mirvinstalk.app.model.response;

import com.mirvinstalk.app.model.IIUser;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterResponse implements Serializable {
    IIUser IIUser;
    ArrayList<ArrayList<Double>> PricesInTRY;
    ArrayList<ArrayList<Double>> PricesInUSD;
    ArrayList<ArrayList<Double>> PricesInEUR;
    String TryForFreeUsername;

    public String getTryForFreeUsername() {
        return TryForFreeUsername;
    }

    public void setTryForFreeUsername(String tryForFreeUsername) {
        TryForFreeUsername = tryForFreeUsername;
    }

    public ArrayList<ArrayList<Double>> getPricesInTRY() {
        return PricesInTRY;
    }

    public void setPricesInTRY(ArrayList<ArrayList<Double>> pricesInTRY) {
        PricesInTRY = pricesInTRY;
    }

    public ArrayList<ArrayList<Double>> getPricesInUSD() {
        return PricesInUSD;
    }

    public void setPricesInUSD(ArrayList<ArrayList<Double>> pricesInUSD) {
        PricesInUSD = pricesInUSD;
    }

    public ArrayList<ArrayList<Double>> getPricesInEUR() {
        return PricesInEUR;
    }

    public void setPricesInEUR(ArrayList<ArrayList<Double>> pricesInEUR) {
        PricesInEUR = pricesInEUR;
    }

    public com.mirvinstalk.app.model.IIUser getIIUser() {
        return IIUser;
    }

    public void setIIUser(com.mirvinstalk.app.model.IIUser IIUser) {
        this.IIUser = IIUser;
    }
}
