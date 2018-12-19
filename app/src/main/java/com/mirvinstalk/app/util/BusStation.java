package com.mirvinstalk.app.util;

import com.squareup.otto.Bus;

public class BusStation {
    private static Bus bus = new Bus();
    public static Bus getBus(){
        /*if(bus!=null){
            return bus;
        }
        else{
            return new Bus();
        }*/
        return bus;
    }

}
