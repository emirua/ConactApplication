package com.emilio.contactapplication;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Emilio on 09/11/2016.
 */

public class MyContactApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
