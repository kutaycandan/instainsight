package com.kutaycandan.instainsight.model;

import java.io.Serializable;

public class IIFeatureOrder implements Serializable{
    String GUID;
    String DateAdded;
    String Name;
    String TimeElapsedTillFinish;
    String IIFeatureState;

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTimeElapsedTillFinish() {
        return TimeElapsedTillFinish;
    }

    public void setTimeElapsedTillFinish(String timeElapsedTillFinish) {
        TimeElapsedTillFinish = timeElapsedTillFinish;
    }

    public String getIIFeatureState() {
        return IIFeatureState;
    }

    public void setIIFeatureState(String IIFeatureState) {
        this.IIFeatureState = IIFeatureState;
    }
}
