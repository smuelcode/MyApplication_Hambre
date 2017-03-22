package com.example.hambre.myapplication;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import android.app.Application;

public class HambreApp extends android.app.Application {

    public void onCreate(){

        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }


}
