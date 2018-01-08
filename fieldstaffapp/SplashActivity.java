package com.fieldstaffapp.fieldstaffapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fieldstaffapp.fieldstaffapp.background_service.SMessageService;
import com.fieldstaffapp.fieldstaffapp.model.valid_user.ValidUserMain;
import com.fieldstaffapp.fieldstaffapp.preference.CustomPrefs;
import com.fieldstaffapp.fieldstaffapp.restservice.RestService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ResultCallback<LocationSettingsResult> {

    private static final int REQUEST_CODE_PHONE_STATE =11 ;
    private GoogleApiClient googleApiClient;
    private GoogleApiAvailability apiAvailability;

    private static final long MIN_TIME_BW_UPDATES = 10000;
    private static final long FAST_TIME_BW_UPDATES = 3000;
    private final static int REQUEST_CHECK_SETTINGS = 100;
    private static final long EXPIRATION_DURATION = 3600000;

    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    private Location location;
    private LatLng latLng = new LatLng(0, 0);
    private double latitude;
    private double longitude;
    private Runnable mRunnable;
    private static final int SPLASH_TIME_OUT = 3000;
    private Handler mHandler = new Handler();
    private ProgressDialog progressBar;
    private boolean isPhoneStatePermission;
    private String imeiNo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        insertPermissionForPhoneState();
        progressBar =new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Loading...");


        apiAvailability = GoogleApiAvailability.getInstance();
        if (googleApiAvaibility()) {
            buildGoogleApiClient();
            googleApiClient.connect();
            if (googleApiClient.isConnected()) {
                displayLocation();
            }
        }



       /* mRunnable = new Runnable() {

                    @Override
                    public void run() {
                       *//* startActivity(new Intent(SplashActivity.this,FormOneActivity.class));
                        finish();*//*
                        if (String.valueOf(latitude)!=null&&String.valueOf(latitude).length()>4){
                           callVerifyUser();
                        }else {
                            Intent intent=new Intent(SplashActivity.this,SplashActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0,0);
                            finish();
                        }



                    }
                };
                mHandler.postDelayed(mRunnable, SPLASH_TIME_OUT);*/

    }

    private void callVerifyUser() {
        if (String.valueOf(latitude)!=null&&String.valueOf(latitude).length()>4&&imeiNo.length()>0){
            progressBar.show();
            Call<ValidUserMain> call =new RestService().restInterface.checkValidUser(FieldStaffApp.FROM_APP,FieldStaffApp.APP_TYPE,"IMEI",
                    imeiNo,String.valueOf(latitude),String.valueOf(longitude));;
            call.enqueue(new Callback<ValidUserMain>() {
                @Override
                public void onResponse(Call<ValidUserMain> call, Response<ValidUserMain> response) {
                    if (response.body().getIsSuccess()){
                        if (progressBar.isShowing())
                            progressBar.dismiss();
                        CustomPrefs.writeString(SplashActivity.this,FieldStaffApp.USER_IMEI_NO,response.body().getImeiInfo().getImeiNumber());
                        Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
                        intent.putExtra(FieldStaffApp.KEY_AREA_TYPE,response.body().getImeiInfo().getAreaType());
                        if (response.body().getImeiInfo().getAreaType().trim().equalsIgnoreCase("C")){
                            intent.putExtra(FieldStaffApp.KEY_CORP_OR_MUNICIPALITY_NAME,response.body().getImeiInfo().getCorporationName());
                            intent.putExtra(FieldStaffApp.KEY_CORP_OR_MUNICIPALITY_ID,response.body().getImeiInfo().getCorporationId());
                            intent.putExtra(FieldStaffApp.KEY_DIV_OR_WORD_NAME,response.body().getImeiInfo().getDivisionName());
                            intent.putExtra(FieldStaffApp.KEY_DIV_OR_WORD_ID,response.body().getImeiInfo().getDivisionId());
                            intent.putExtra(FieldStaffApp.KEY_IMEI_ID, response.body().getImeiInfo().getImeiId());
                        }else if (response.body().getImeiInfo().getAreaType().trim().equalsIgnoreCase("M")){
                            intent.putExtra(FieldStaffApp.KEY_CORP_OR_MUNICIPALITY_NAME,response.body().getImeiInfo().getMunicipalityName());
                            intent.putExtra(FieldStaffApp.KEY_CORP_OR_MUNICIPALITY_ID,response.body().getImeiInfo().getMunicipalityId());
                            intent.putExtra(FieldStaffApp.KEY_DIV_OR_WORD_NAME,response.body().getImeiInfo().getWardName());
                            intent.putExtra(FieldStaffApp.KEY_DIV_OR_WORD_ID,response.body().getImeiInfo().getWardId());
                            intent.putExtra(FieldStaffApp.KEY_IMEI_ID, response.body().getImeiInfo().getImeiId());
                        }

                        startService(new Intent(SplashActivity.this, SMessageService.class));
                        startActivity(intent);
                        finish();
                    }else {
                       // Toast.makeText(SplashActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT)
                           //     .show();
                       FieldStaffApp.showDialog(SplashActivity.this,response.body().getMessage());
                    }


                }

                @Override
                public void onFailure(Call<ValidUserMain> call, Throwable t) {
                    if (progressBar.isShowing())
                        progressBar.dismiss();
                    Toast.makeText(SplashActivity.this, "failed to loading...", Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }else {
            Intent intent=new Intent(SplashActivity.this,SplashActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();
        }
    }

    /*@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public String getIMEI(){
        isPhoneStatePermission=true;

        TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;

    }*/
    private void getIMEI(){
        isPhoneStatePermission=true;
        TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
         imeiNo = mngr.getDeviceId();
    }
    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

        }
    }
    protected void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(MIN_TIME_BW_UPDATES);
        mLocationRequest.setFastestInterval(FAST_TIME_BW_UPDATES);
        mLocationRequest.setSmallestDisplacement(10);
        mLocationRequest.setExpirationDuration(EXPIRATION_DURATION);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private boolean googleApiAvaibility() {
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        switch (resultCode) {
            case ConnectionResult.SUCCESS:
                return true;
            case ConnectionResult.API_UNAVAILABLE:
                Dialog dialog = apiAvailability.getErrorDialog(this, ConnectionResult.API_UNAVAILABLE, 200,
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                //finish();
                            }
                        });
                dialog.show();
                break;
            case ConnectionResult.SERVICE_MISSING:
                Dialog dialog1 = apiAvailability.getErrorDialog(this, ConnectionResult.SERVICE_MISSING, 201,
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                //finish();
                            }
                        });
                dialog1.show();
                break;
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Toast.makeText(SplashActivity.this,latLng.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        displayLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        //finish();
//                        Toast.makeText(getActivity(), "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                      //  FieldStaffApp.showDialog(this,"Please enable location. Otherwise you can not use this application");
                        break;
                }
                break;
            default:

                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        createLocationRequest();
        checkLocationSettings();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        googleApiClient.connect();
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult result) {
        Status status = result.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                displayLocation();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.e("Exception", e.toString());
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                FieldStaffApp.showDialog(this,"You have choose never for Location!");
                break;
            default:
                break;
        }
    }

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.
                checkLocationSettings(googleApiClient, mLocationSettingsRequest);
        result.setResultCallback(this);
    }

    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 250);
        } else {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                System.out.println(String.valueOf(latitude)+String.valueOf(longitude));
               // Toast.makeText(SplashActivity.this,"displayLocation"+String.valueOf(latitude)+String.valueOf(longitude),Toast.LENGTH_SHORT);
                if (FieldStaffApp.getInstance().isConnectionPossible()){
                    callVerifyUser();
                }else{
                    FieldStaffApp.showDialog(SplashActivity.this,getResources().getString(R.string.err_msg_internet));
                }

                if (FieldStaffApp.getInstance().isConnectionPossible()) {
                    Geocoder gcd = new Geocoder(SplashActivity.this, Locale.getDefault());
                    List<Address> addresses = null;
                    String addressName;
                    try {
                        addresses = gcd.getFromLocation(latitude, longitude, 1);
                        if (addresses.size() > 0) {
                            addressName = addresses.get(0).getLocality();
                            /*Address address = addresses.get(0);
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                                sb.append(address.getAddressLine(i)).append(" ");
                            }*/
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                googleApiClient.connect();
                if (googleApiClient.isConnected()) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
                }
            }
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (location != null) {
                    displayLocation();
                } else {
                    googleApiClient.connect();
                }

            }
        }
    }*/
    private void insertPermissionForPhoneState() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("READ_PHONE_STATE");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(this, message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(SplashActivity.this,
                                        permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_PHONE_STATE);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_PHONE_STATE);
            return;
        }

        getIMEI();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (location != null) {
                    displayLocation();
                } else {
                    googleApiClient.connect();
                }

            }
        }
        if (requestCode == REQUEST_CODE_PHONE_STATE) {
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial

                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    getIMEI();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }  else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public static void showMessageOKCancel(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
