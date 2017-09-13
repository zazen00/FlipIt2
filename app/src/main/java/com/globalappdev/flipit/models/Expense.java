package com.globalappdev.flipit.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Owner on 9/2/2017.
 */

@IgnoreExtraProperties
public class Expense {
    public Expense() {
    }

    public String item;
    public int qty;
    public float cost;
    public String url;

    public Expense(String item, int qty, float cost, String url) {
        this.item = item;
        this.qty = qty;
        this.cost = cost;
        this.url = url;
    }
}
