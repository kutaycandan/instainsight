package com.kutaycandan.instainsight.model;

import java.io.Serializable;
import java.util.ArrayList;

public class IIInvoice implements Serializable {
    String DateAdded;
    String ServiceDueDate;
    String Code;
    String Target_InstaUser; //Order Features
    ArrayList<IIFeatureOrder> IIFeatureOrders;

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }

    public String getServiceDueDate() {
        return ServiceDueDate;
    }

    public void setServiceDueDate(String serviceDueDate) {
        ServiceDueDate = serviceDueDate;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getTarget_InstaUser() {
        return Target_InstaUser;
    }

    public void setTarget_InstaUser(String target_InstaUser) {
        Target_InstaUser = target_InstaUser;
    }

    public ArrayList<IIFeatureOrder> getIIFeatureOrders() {
        return IIFeatureOrders;
    }

    public void setIIFeatureOrders(ArrayList<IIFeatureOrder> IIFeatureOrders) {
        this.IIFeatureOrders = IIFeatureOrders;
    }
}
