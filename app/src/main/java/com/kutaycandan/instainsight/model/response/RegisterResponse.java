package com.kutaycandan.instainsight.model.response;

import com.kutaycandan.instainsight.model.IIUser;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterResponse implements Serializable {
    IIUser IIUser;
    ArrayList<ArrayList<Double>> PricesInTRY;
    ArrayList<ArrayList<Double>> PricesInUSD;
    ArrayList<ArrayList<Double>> PricesInEUR;


    public com.kutaycandan.instainsight.model.IIUser getIIUser() {
        return IIUser;
    }

    public void setIIUser(com.kutaycandan.instainsight.model.IIUser IIUser) {
        this.IIUser = IIUser;
    }
}
