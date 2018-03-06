package com.jolameva.app.runningdinner.helperclass;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by guemuesm on 06.03.2018.
 */
    public class MyApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            FacebookSdk.sdkInitialize(this);
        }
    }

