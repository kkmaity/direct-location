/*
 * Decompiled with CFR 0_92.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.PendingIntent
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.os.AsyncTask
 *  android.os.IBinder
 *  com.google.analytics.tracking.android.EasyTracker
 *  com.google.analytics.tracking.android.MapBuilder
 *  com.google.analytics.tracking.android.StandardExceptionParser
 *  java.io.BufferedReader
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 *  java.util.Map
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.client.entity.UrlEncodedFormEntity
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.message.BasicNameValuePair
 */
package com.fieldstaffapp.fieldstaffapp.background_service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat.Builder;

import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.fieldstaffapp.fieldstaffapp.ExistingPlantActivity;
import com.fieldstaffapp.fieldstaffapp.FieldStaffApp;
import com.fieldstaffapp.fieldstaffapp.R;
import com.fieldstaffapp.fieldstaffapp.model.auto_update.AutoUpdateLocation;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Failed to analyse overrides
 */
public class SMessageService extends Service implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,
		LocationListener, ResultCallback<LocationSettingsResult> {
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
	public Context context = this;
	Runnable runnable;

	private void restartService() {
		//Toast.makeText(this, "Restaer Service", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent((Context)this, SReceiverInterval.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast((Context)this.getApplicationContext(), 30000, (Intent)intent, 0);
		((AlarmManager)this.getSystemService(ALARM_SERVICE)).setRepeating(0, System.currentTimeMillis() + (long)70000, System.currentTimeMillis() + (long)70000, pendingIntent);
	}

	public IBinder onBind(Intent intent) {
		//Toast.makeText(this, "onBind", Toast.LENGTH_SHORT).show();
		return null;
	}

	public void onCreate() {
		super.onCreate();

	}

	public void onDestroy() {
		super.onDestroy();

	}
	/*
	 * Enabled aggressive block sorting
	 * Enabled unnecessary exception pruning
	 */
	@SuppressLint("NewApi")
	public int onStartCommand(Intent intent, int n, int n2) {
		this.restartService();

		try {
			upload();

		}
		catch (Exception var4_4) {

			return super.onStartCommand(intent, n, n2);
		}
		return super.onStartCommand(intent, n, n2);
	}


	public void getAccessToken(){

		// TODO Auto-generated method stub
		super.onCreate();



	}
	void upload()
	{
		runnable=new Runnable() {
			@Override
			public void run() {
				Message msgObj = handler.obtainMessage();
				msgObj.what=1;
				handler.sendMessage(msgObj);
				apiAvailability = GoogleApiAvailability.getInstance();
				if (googleApiAvaibility()) {
					buildGoogleApiClient();
					googleApiClient.connect();
					if (googleApiClient.isConnected()) {
						displayLocation();
					}
				}
				//Toast.makeText(context, "111111......", Toast.LENGTH_SHORT).show();
			}
		};handler.postDelayed(runnable,20000);
		/*;
		//generateNotification();
*/

	}

	//==================Handeler for message=======================//

	private final Handler handler = new Handler() {

		public void handleMessage(Message msg) {

			if(msg.what==1)
			{
				upload();

			}

		}

	};

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

				break;
			case ConnectionResult.SERVICE_MISSING:

				break;
		}
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		latLng = new LatLng(location.getLatitude(), location.getLongitude());
		//Toast.makeText(this,latLng.toString(),Toast.LENGTH_SHORT).show();
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
				/*try {
					status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
				} catch (IntentSender.SendIntentException e) {
					Log.e("Exception", e.toString());
				}*/
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
			/*ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 250)*/;
		} else {
			location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
			if (location != null) {
				latLng = new LatLng(location.getLatitude(), location.getLongitude());
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				System.out.println(String.valueOf(latitude)+String.valueOf(longitude));
				if (String.valueOf(latitude).length()>4&&FieldStaffApp.getInstance().isConnectionPossible()) {
					//Toast.makeText(context, "111111......"+String.valueOf(latitude)+String.valueOf(longitude), Toast.LENGTH_SHORT).show();
					autoUpdateLocation(String.valueOf(latitude),String.valueOf(longitude));
				}
			} else {
				googleApiClient.connect();
				if (googleApiClient.isConnected()) {
					LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
				}
			}
		}
	}

	private void autoUpdateLocation(final String lat,final String longit) {
		Call<AutoUpdateLocation> call= RestService.getInstance().restInterface.autoUpdateLocation(FieldStaffApp.FROM_APP,FieldStaffApp.APP_TYPE,"PLANTLOCATION", CustomPrefs.readString(context,FieldStaffApp.USER_IMEI_NO,""),String.valueOf(latitude),String.valueOf(longitude));
		call.enqueue(new Callback<AutoUpdateLocation>() {
			@Override
			public void onResponse(Call<AutoUpdateLocation> call, Response<AutoUpdateLocation> response) {
				if (response.body().getIsSuccess()){
					System.out.println("Call Background Service for auto location update.......");
					//Intent intent=new Intent(context, ExistingPlantActivity.class);
					if (response.body().getPlantLocationInfo().getMsg().equalsIgnoreCase("Plant found")) {
						/*whichActivityRunning();
						if (whichActivityRunning().equalsIgnoreCase("com.fieldstaffapp.fieldstaffapp.ExistingPlantActivity")){

						}else
						generateNotification();*/
						Intent i = new Intent("android.intent.action.MATCH_LOCATION").
								putExtra("some_msg", response.body().getPlantLocationInfo().getMsg()).
								putExtra("latitude",lat).putExtra("longitude",longit);

						//intent.putExtra(FieldStaffApp.KEY_MATCH_YOUR_LOCATION,FieldStaffApp.MATCH_LOCATION_RESULT);
						sendBroadcast(i);
					}
				}
			}

			@Override
			public void onFailure(Call<AutoUpdateLocation> call, Throwable t) {

			}
		});
	}
	void generateNotification(){

		Intent intent = new Intent(this, ExistingPlantActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
		PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		Notification notification = builder.setContentTitle("Field Staff Application")
				.setContentText("Find a plant tagged in your current location.")
				.setSmallIcon(R.mipmap.ic_launcher)
				.setAutoCancel(true)
				.setContentIntent(pIntent).build();

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}
	protected String whichActivityRunning()
	{
		ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
		Log.d("topActivity", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName());
		ComponentName componentInfo = taskInfo.get(0).topActivity;
		String classNameP=componentInfo.getClassName();
		return classNameP;

	}
}

