package com.fieldstaffapp.fieldstaffapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.widget.EditText;

import com.fieldstaffapp.fieldstaffapp.preference.CustomPrefs;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FieldStaffApp extends Application {
    private static FieldStaffApp instance;
    public static final String USER_IMEI_NO = "imei";
    public static final String KEY_IMEI_ID = "imei_id";
    public static final String PREF_NAME = "FieldStaffApp";
    public static final int PREFERENCE_MODE = Context.MODE_PRIVATE;
    public static final String APP_TYPE = "ANDROID";
    public static final String FROM_APP = "1";
    public static final String KEY_TAGGING = "new_tagging";
    public static String deviceUniqueId;
    public static List<String> plantProtectionList=new ArrayList<>();
    public static List<String> plantationTypeList=new ArrayList<>();
    public static List<String> blockPlantationList=new ArrayList<>();
    public static List<String> PlantationConditionList=new ArrayList<>();
    public static final String KEY_AREA_TYPE="area_type";
    public static final String KEY_CORP_OR_MUNICIPALITY_NAME="corp_name_min";
    public static final String KEY_DIV_OR_WORD_NAME="div_word_name";
    public static final String KEY_CORP_OR_MUNICIPALITY_ID="corp_min_id";
    public static final String KEY_DIV_OR_WORD_ID="div_word_id";
    public static final String KEY_PLANT_ID="plant_id";
    public static final String KEY_PLANT_NO="plant_no";
    public static final String KEY_GEOTAGGING_ID="geo_id";
    public static final String KEY_IS_EXISTING="is_exist";
    public static final String VALUE_EXISTING="existing_plant";
    public static final String KEY_EXISTING_PLANT_IMAGE_ID="plant_image_id";
    public static final String KEY_EXISTING_PLANT_ID="pl_id";
    public static final String KEY_EXISTING_PLANT_NAME="pla_na";
    public static final String KEY_EXISTING_PLANT_NO="no";
    public static final String  KEY_EXISTING_GEOTAGGING_ID="exis_geo_id";
    public static final String KEY_EXISTING_PLANT_STREET_ADDRESS="address";
    public static final String KEY_EXISTING_PLANT_HEIGHT="height";
    public static final String KEY_EXISTING_PLANT_CONDITION="condition";
    public static final String KEY_EXISTING_PLANT_PROTECTION="protection";
    public static final String KEY_EXISTING_PLANTATION_TYPE="plantation_type";
    public static final String KEY_EXISTING_PLANTATION_BLOCK="block_plant";
    public static final String KEY_EXISTING_PLANTAT_ADOPTER="adopter";
    public static final String KEY_EXISTING_PLANTAT_ADOPTER_PHONE="phone_no";


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        deviceUniqueId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        plantProtectionList.add(0,"Cement");
        plantProtectionList.add(1,"Mesh");
        plantProtectionList.add(2,"No");
        plantProtectionList.add(3,"Other");
        plantationTypeList.add(0,"Avenue Plantation");
        plantationTypeList.add(1,"Block Plantation");
        plantationTypeList.add(2,"Household Plantation");
        blockPlantationList.add(0,"Government Offices");
        blockPlantationList.add(1,"Private Offices");
        blockPlantationList.add(2,"Open site Plantation");
        blockPlantationList.add(3,"Parks & Playgrounds");
        blockPlantationList.add(4,"Government Schools");
        blockPlantationList.add(5,"Private Schools");
        blockPlantationList.add(6,"Burial Grounds");
        blockPlantationList.add(7,"Holy Places");
        blockPlantationList.add(8,"Others");
        PlantationConditionList.add(0,"Good");
        PlantationConditionList.add(1,"Dead Condition");

    }

    public static FieldStaffApp getInstance() {
        return instance;
    }






    public boolean isEdtTxtEmpty(EditText editText) {
        int strLen = editText.getText().toString().trim().length();
        if (strLen > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isStrEqual(EditText editText1, EditText editText2) {
        String firstString = editText1.getText().toString().trim();
        String secondString = editText2.getText().toString().trim();
        if (firstString.equalsIgnoreCase(secondString))
            return false;
        else
            return true;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public int getDisplayHeight() {
        return getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    public int getDisplayWidth() {
        return getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    public boolean isConnectionPossible() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    public static void showDialog(Context context, int msg) {
        new AlertDialog.Builder(context).setMessage(msg).setCancelable(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public static void showDialog(Context context, String msg) {
        new AlertDialog.Builder(context).setMessage(msg).setCancelable(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getPath(Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(contentUri, null, null);
            }
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                switch (type) {
                    case "image":
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "video":
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "audio":
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        break;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(contentUri, selection, selectionArgs);
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri)) return uri.getLastPathSegment();
            return getDataColumn(uri, null, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public String getDataColumn(Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }

        return null;
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static void recycleUsedBitmap(Bitmap bmp) {
        if (bmp != null && !bmp.isRecycled()) {
            bmp.recycle();
            bmp = null;
        }
    }
    @SuppressLint("SimpleDateFormat")
    public static String convertTimestampToDate(long timestamp) {
        Timestamp tStamp = new Timestamp(timestamp);
        SimpleDateFormat simpleDateFormat;
        if (DateUtils.isToday(timestamp)) {
            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            return "Today " + simpleDateFormat.format(tStamp);
        } else {
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            return simpleDateFormat.format(tStamp);
        }
    }

    public static long correctTimestamp(long timestampInMessage) {
        long correctedTimestamp = timestampInMessage;

        if (String.valueOf(timestampInMessage).length() < 13) {
            int difference = 13 - String.valueOf(timestampInMessage).length(), i;
            String differenceValue = "1";
            for (i = 0; i < difference; i++) {
                differenceValue += "0";
            }
            correctedTimestamp = (timestampInMessage * Integer.parseInt(differenceValue))
                    + (System.currentTimeMillis() % (Integer.parseInt(differenceValue)));
        }
        return correctedTimestamp;
    }


}
