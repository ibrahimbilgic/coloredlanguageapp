package com.coloredlanguageapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {
    public PreferenceUtils(){

    }
    public static boolean saveEmail(String email, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(Constants.KEY_EMAIL,email);
        prefsEditor.apply();
        return true;
    }
    public static String getEmail(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_EMAIL,null);
    }
    public static boolean saveName(String name,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(Constants.KEY_NAME,name);
        prefsEditor.apply();;
        return true;
    }
    public static String getName(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_NAME,null);
    }
    public static boolean isNightMode(boolean nightMode,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putBoolean(Constants.KEY_ISNIGHTMODE,nightMode);
        prefsEditor.apply();
        return true;
    }
    public static boolean isNightModeCheck(Context context){
        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(Constants.KEY_ISNIGHTMODE,false);
    }
    public static boolean isNightModeSystem(boolean nightModeSystem,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putBoolean(Constants.KEY_ISNIGHTMODESYSTEM,nightModeSystem);
        prefsEditor.apply();
        return true;
    }
    public static boolean isNightModeSystemCheck(Context context){
        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(Constants.KEY_ISNIGHTMODESYSTEM,false);
    }

}
