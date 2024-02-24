package com.moutimid.bookingapp.helper;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.fxn.stash.Stash;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Stash.init(this);

    }
}