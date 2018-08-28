package com.kutaycandan.instainsight.model.response;

import java.io.Serializable;

public class StalkAmountResponse implements Serializable {
    Integer StalkAmount;

    public Integer getStalkAmount() {
        return StalkAmount;
    }

    public void setStalkAmount(Integer stalkAmount) {
        StalkAmount = stalkAmount;
    }
}
