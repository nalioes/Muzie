package com.syncsource.org.muzie;

import android.app.Application;
import android.content.Context;

/**
 * Created by SyncSource on 9/4/2016.
 */
public class MuzieApp extends Application {

    private static Context context;
    public static String TAG = "Muzie";
    public static MuzieApp MUSIE;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        MUSIE = this;

    }

    public static Context getContext() {
        return context;
    }
}
