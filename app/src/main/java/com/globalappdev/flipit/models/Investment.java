package com.globalappdev.flipit.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Owner on 8/31/2017.
 */
@IgnoreExtraProperties
public class Investment {
    public String prop;
    public String address;
    public Expenses sub;
    public String bg;

    public Investment() {
    }
    //    public String





    public String getAddress() {
        return address;
    }


    public Expenses getSub() {
        return sub;
    }

    public void setSub(Expenses sub) {
        this.sub = sub;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    /**
     *
     * @param prop
     * @param address

     * @param expenses
     * @param bg
     */
    public Investment(String prop, String address, Expenses expenses, String bg) {
        this.prop = prop;
        this.address = address;
        this.sub = expenses;
        this.bg = bg;
    }
}
