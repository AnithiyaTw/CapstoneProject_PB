package com.example.capstoneproject_pb;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DarkMode extends Application {

    public static final String NIGHT_MODE = "NIGHT_MODE";
    private boolean isNightModeEnabled = false;
    private static DarkMode singleton = null;

    public static DarkMode getInstance() {
        if(singleton == null)
        {
            singleton = new DarkMode();
        }
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.isNightModeEnabled = mPrefs.getBoolean(NIGHT_MODE, false);
    }

    public boolean isNightModeEnabled() {
        return isNightModeEnabled;
    }

    public void setIsNightModeEnabled(boolean isNightModeEnabled) {
        this.isNightModeEnabled = isNightModeEnabled;
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(NIGHT_MODE, isNightModeEnabled);
        editor.apply();
    }
}