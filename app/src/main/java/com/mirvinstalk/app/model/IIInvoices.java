package com.mirvinstalk.app.model;

import java.io.Serializable;

public class IIInvoices implements Serializable {
    String DateAdded;
    String ServiceDueDate;
    String InstaUsername;
    String Code;

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

    public String getInstaUsername() {
        return InstaUsername;
    }

    public void setInstaUsername(String instaUsername) {
        InstaUsername = instaUsername;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
