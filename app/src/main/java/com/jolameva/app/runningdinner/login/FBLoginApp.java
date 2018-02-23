package com.jolameva.app.runningdinner.login;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Metin on 21.02.2018.
 */

public class FBLoginApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());

        Fresco.initialize(this);
    }
}
