package com.jolameva.app.runningdinner.helperclass;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by guemuesm on 06.03.2018.
 */
    public class MyApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            FacebookSdk.sdkInitialize(this);
            AndroidThreeTen.init(this);

//            if(!FirebaseApp.getApps(this).isEmpty()){
//                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//            }
        }
    }

