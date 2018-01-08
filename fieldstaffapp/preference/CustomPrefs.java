package com.fieldstaffapp.fieldstaffapp.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.fieldstaffapp.fieldstaffapp.FieldStaffApp;


public class CustomPrefs {

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(FieldStaffApp.PREF_NAME,
                FieldStaffApp.PREFERENCE_MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key,
                                      boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static boolean removeSpecficObject(Context context, String key) {
        return getEditor(context).remove("text").commit();
    }

    public static boolean removeAllObject(Context context) {
        return getEditor(context).clear().commit();
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();

    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }
}
