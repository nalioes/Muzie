package com.syncsource.org.muzie;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import io.fabric.sdk.android.Fabric;

/**
 * Created by SyncSource on 9/4/2016.
 */
public class MuzieApp extends Application {

    public static Context context;
    public static String TAG = "Muzie";
    public static MuzieApp MUSIE;
    private Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = getApplicationContext();
        MUSIE = this;

    }

    public static Context getContext() {
        return context;
    }

    public Tracker getTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            tracker = analytics.newTracker("UA-84999042-1");
        }
        return tracker;
    }
}
