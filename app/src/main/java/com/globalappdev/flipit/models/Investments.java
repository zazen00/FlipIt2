package com.globalappdev.flipit.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 8/31/2017.
 */
@IgnoreExtraProperties
public class Investments {

    public List<Investment> investments = new ArrayList<>();
    public Investments() {
    }

    public Investments(Investment... investments) {
        for(Investment i:investments){
            this.investments.add(i);
        }
    }

    public List<Investment> add(Investment i){
        investments.add(i);
        return investments;
    }

    public List<Investment> getInvestments() {
        return investments;
    }
}
