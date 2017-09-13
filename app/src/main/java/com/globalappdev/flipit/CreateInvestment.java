package com.globalappdev.flipit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Owner on 9/5/2017.
 */

public class CreateInvestment extends AppCompatActivity {
    String TAG = "CreateInvestment";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate Called");
    }
}
