package com.fieldstaffapp.fieldstaffapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fieldstaffapp.fieldstaffapp.model.ImageUpload.ImageUploadMain;
import com.fieldstaffapp.fieldstaffapp.model.existing_image_upload.ExistingImageUp;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormTwoActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ResultCallback<LocationSettingsResult> {

    private static final int REQUEST_CODE_CAMERA = 12;
    private static final int REQUEST_CODE_GALLERY = 21;
    private static final int TAKE_PHOTO_REQUEST = 34;

    private GoogleApiClient googleApiClient;
    private GoogleApiAvailability apiAvailability;

    private static final long MIN_TIME_BW_UPDATES = 5000;
    private static final long FAST_TIME_BW_UPDATES = 2000;
    private final static int REQUEST_CHECK_SETTINGS = 100;
    private static final long EXPIRATION_DURATION = 3600000;

    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    private Location location;
    private LatLng latLng = new LatLng(0, 0);
    private double latitude;
    private double longitude;
    private ImageView imageViewOne;
    private ImageView imageViewTwo;
    private ImageView imageViewThree;
    private ImageView imageViewFour;
    private Uri mImageCaptureUri;
    private int imageType;
    private Bitmap bmp2;
    private Bitmap bmp3;
    private Bitmap bmp4;
    private Bitmap bmp1;
    private String lat1="";
    private String lat2="";
    private String lat3="";
    private String lat4="";
    private String long1="";
    private String long2="";
    private String long3="";
    private String long4="";
    private File file2;
    private File file3;
    private File file4;
    private File file1;
    private String  PlantNo;
    private String PlantId;
    private String GeotaggingId;
    private ProgressDialog progressDialog;
    private Button btnSubmitReport;
    private LinearLayout linearExisting;
    private ImageView imageViewExistingImage;
    private String existingPlant;
    private LinearLayout linearNewTagging;
    private boolean isExistingGeoTagging;
    private Bitmap bmp5;
    private File file5;
    private String plantImageId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_two);
        init();

    }

    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView backImgView = (ImageView) findViewById(R.id.backImgView);
        TextView titleTxtView = (TextView) findViewById(R.id.titleTxtView);
        backImgView.setOnClickListener(this);
        progressDialog=new ProgressDialog(FormTwoActivity.this);
        progressDialog.setCancelable(false);
        linearExisting=(LinearLayout)findViewById(R.id.linearExisting);
        linearNewTagging=(LinearLayout)findViewById(R.id.linearNewTagging);
        imageViewExistingImage=(ImageView)findViewById(R.id.imageViewExistingImage);
        plantImageId=getIntent().getStringExtra(FieldStaffApp.KEY_EXISTING_PLANT_IMAGE_ID);
        PlantNo=getIntent().getStringExtra(FieldStaffApp.KEY_PLANT_NO);
        PlantId=getIntent().getStringExtra(FieldStaffApp.KEY_PLANT_ID);
        GeotaggingId=getIntent().getStringExtra(FieldStaffApp.KEY_GEOTAGGING_ID);
         existingPlant = getIntent().getStringExtra(FieldStaffApp.KEY_IS_EXISTING);

        if (existingPlant!=null&&existingPlant.equalsIgnoreCase(FieldStaffApp.VALUE_EXISTING)){
            isExistingGeoTagging=true;
            linearNewTagging.setVisibility(View.GONE);
            linearExisting.setVisibility(View.VISIBLE);
        }else {
            isExistingGeoTagging=false;
            linearExisting.setVisibility(View.GONE);
            linearNewTagging.setVisibility(View.VISIBLE);
        }

        apiAvailability = GoogleApiAvailability.getInstance();
        if (googleApiAvaibility()) {
            buildGoogleApiClient();
            googleApiClient.connect();
            if (googleApiClient.isConnected()) {
                displayLocation();
            }
        }

        btnSubmitReport=(Button)findViewById(R.id.btnSubmitReport);
        imageViewOne=(ImageView)findViewById(R.id.imageViewOne);
        imageViewTwo=(ImageView)findViewById(R.id.imageViewTwo);
        imageViewThree=(ImageView)findViewById(R.id.imageViewThree);
        imageViewFour=(ImageView)findViewById(R.id.imageViewFour);
        imageViewOne.setOnClickListener(this);
        imageViewTwo.setOnClickListener(this);
        imageViewThree.setOnClickListener(this);
        imageViewFour.setOnClickListener(this);
        btnSubmitReport.setOnClickListener(this);
        imageViewExistingImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSubmitReport:
                if (isValidate()){
                    if (isExistingGeoTagging){
                        uploadForExistingTagging();
                    }else {
                        uploadImage();
                    }
                }
                break;
            case R.id.backImgView:
                finish();
                break;
            case R.id.imageViewOne:
                imageType=1;
                insertDummyCameraWrapperPhoto();
                break;
            case R.id.imageViewTwo:
                imageType=2;
                insertDummyCameraWrapperPhoto();
                break;
            case R.id.imageViewThree:
                imageType=3;
                insertDummyCameraWrapperPhoto();
                break;
            case R.id.imageViewFour:
                imageType=4;
                insertDummyCameraWrapperPhoto();
                break;
            case R.id.imageViewExistingImage:
                imageType=5;
                insertDummyCameraWrapperPhoto();
                break;
        }


    }

    private void uploadForExistingTagging() {
        if (FieldStaffApp.getInstance().isConnectionPossible()){
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
            MultipartBody.Part body = null;
            RequestBody requestBodyOrderDataFile1 = null;
            RequestBody requestBodyFromApp = RequestBody.create(
                    MediaType.parse("multipart/form-data"), FieldStaffApp.FROM_APP);
            RequestBody requestBodyAppType = RequestBody.create(
                    MediaType.parse("multipart/form-data"), FieldStaffApp.APP_TYPE);
            RequestBody requestBodyPageType = RequestBody.create(
                    MediaType.parse("multipart/form-data"), "UPLOADEXISTING");
            RequestBody requestBodylat1 = RequestBody.create(
                    MediaType.parse("multipart/form-data"),String.valueOf(latitude));
            RequestBody requestBodylong1 = RequestBody.create(
                    MediaType.parse("multipart/form-data"), String.valueOf(longitude));
            RequestBody requestBodyPlantNo = RequestBody.create(
                    MediaType.parse("multipart/form-data"), PlantNo);
            RequestBody requestBodyPlantId = RequestBody.create(
                    MediaType.parse("multipart/form-data"), PlantId);

            RequestBody requestBodyGeotaggingId = RequestBody.create(
                    MediaType.parse("multipart/form-data"), GeotaggingId);
            RequestBody requestBodyImageId = RequestBody.create(
                    MediaType.parse("multipart/form-data"),plantImageId);
            if (file5 != null && file5.exists()) {
                requestBodyOrderDataFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file5);
                body = MultipartBody.Part.createFormData("letest_image", file5.getName(), requestBodyOrderDataFile1);
            }

            Call<ExistingImageUp> call=RestService.getInstance().restInterface.uploadImageForExistingTree(requestBodyFromApp,requestBodyAppType,requestBodyPageType,requestBodyPlantNo,requestBodyPlantId,requestBodylat1,requestBodylong1,requestBodyGeotaggingId,requestBodyImageId,body);
            call.enqueue(new Callback<ExistingImageUp>() {
                @Override
                public void onResponse(Call<ExistingImageUp> call, Response<ExistingImageUp> response) {
                   if(progressDialog.isShowing())
                       progressDialog.dismiss();
                    if (response.body().getIsSuccess()){
                        showDialogNavigateHone(response.body().getMessage());
                    }else {
                        FieldStaffApp.showDialog(FormTwoActivity.this,response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ExistingImageUp> call, Throwable t) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            });

        }else
        FieldStaffApp.showDialog(FormTwoActivity.this,getResources().getString(R.string.err_msg_internet));

    }

    private boolean isValidate() {
        if (String.valueOf(latitude).length()<=4){
            FieldStaffApp.showDialog(FormTwoActivity.this,"Unable to find your the location");
           return false;
        }
        else if ((bmp1==null)&&(bmp2==null)&&(bmp3==null)&&(bmp4==null)&&(bmp5==null)) {
            FieldStaffApp.showDialog(FormTwoActivity.this,"Please take at least one image for uploading");
            return false;
        }

        return true;
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
                        FieldStaffApp.showDialog(this,"Please enable location. Otherwise you can not use this application");
                        break;
                }
                break;
            case TAKE_PHOTO_REQUEST:
                if (mImageCaptureUri!=null);
                try {
                   if (imageType==1){
                       bmp1 = resizedBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageCaptureUri));
                       if (bmp1!=null){
                           imageViewOne.setImageBitmap(bmp1);
                           file1=convertBitmapToFile(bmp1);
                       }

                       lat1=String.valueOf(latitude);
                       long1=String.valueOf(longitude);
                   }else if (imageType==2)
                   {
                       bmp2 = resizedBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageCaptureUri));
                       if (bmp2!=null){
                           imageViewTwo.setImageBitmap(bmp2);
                           file2=convertBitmapToFile(bmp2);
                       }
                       lat2=String.valueOf(latitude);
                       long2=String.valueOf(longitude);
                   }else if (imageType==3){
                       bmp3 =  resizedBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageCaptureUri));
                       if (bmp3!=null){
                           imageViewThree.setImageBitmap(bmp3);
                           file3=convertBitmapToFile(bmp3);
                       }

                       lat3=String.valueOf(latitude);
                       long3=String.valueOf(longitude);
                   }else if (imageType==4){
                       bmp4 =  resizedBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageCaptureUri));
                       if (bmp4!=null){
                           imageViewFour.setImageBitmap(bmp4);
                           file4=convertBitmapToFile(bmp4);
                       }
                       lat4=String.valueOf(latitude);
                       long4=String.valueOf(longitude);
                   }else if (imageType==5){
                       bmp5 =  resizedBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageCaptureUri));
                       if (bmp5!=null){
                           imageViewExistingImage.setImageBitmap(bmp5);
                           file5=convertBitmapToFile(bmp5);
                       }
                       lat4=String.valueOf(latitude);
                       long4=String.valueOf(longitude);
                   }






                }catch (Exception e){

                }
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
                if (FieldStaffApp.getInstance().isConnectionPossible()) {
                    Geocoder gcd = new Geocoder(FormTwoActivity.this, Locale.getDefault());
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


    private void insertDummyCameraWrapperPhoto() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("CAMERA");

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
                                ActivityCompat.requestPermissions(FormTwoActivity.this,
                                        permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_CAMERA);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_CAMERA);
            return;
        }

        openCamera();
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
        if (requestCode == REQUEST_CODE_CAMERA) {
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    openCamera();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        } else if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // openGallery();
            } else {
                Toast.makeText(this, "Permission not granted  ", Toast.LENGTH_SHORT).show();
            }
        }  else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalCacheDir = getExternalCacheDir();
        if (externalCacheDir != null) {
            mImageCaptureUri = Uri.fromFile(new File(externalCacheDir, "tmp_avatar_"
                    + String.valueOf(System.currentTimeMillis()) + ".jpg"));

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

            try {
                startActivityForResult(cameraIntent, TAKE_PHOTO_REQUEST);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    void uploadImage(){
        if (FieldStaffApp.getInstance().isConnectionPossible()){
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
            MultipartBody.Part body1 = null;
            MultipartBody.Part body2 = null;
            MultipartBody.Part body3 = null;
            MultipartBody.Part body4 = null;
            RequestBody requestBodyOrderDataFile1 = null;
            RequestBody requestBodyOrderDataFile2 = null;
            RequestBody requestBodyOrderDataFile3 = null;
            RequestBody requestBodyOrderDataFile4 = null;
            RequestBody requestBodyFromApp = RequestBody.create(
                    MediaType.parse("multipart/form-data"), FieldStaffApp.FROM_APP);
            RequestBody requestBodyAppType = RequestBody.create(
                    MediaType.parse("multipart/form-data"), FieldStaffApp.APP_TYPE);
            RequestBody requestBodyPageType = RequestBody.create(
                    MediaType.parse("multipart/form-data"), "TREEIMAGES");
            RequestBody requestBodylat1 = RequestBody.create(
                    MediaType.parse("multipart/form-data"), String.valueOf(latitude));
            RequestBody requestBodylong1 = RequestBody.create(
                    MediaType.parse("multipart/form-data"), String.valueOf(longitude));
            RequestBody requestBodylat2 = RequestBody.create(
                    MediaType.parse("multipart/form-data"), String.valueOf(latitude));
            RequestBody requestBodylong2 = RequestBody.create(
                    MediaType.parse("multipart/form-data"), String.valueOf(longitude));
            RequestBody requestBodylat3 = RequestBody.create(
                    MediaType.parse("multipart/form-data"),  String.valueOf(latitude));
            RequestBody requestBodylong3 = RequestBody.create(
                    MediaType.parse("multipart/form-data"), String.valueOf(longitude));
            RequestBody requestBodylat4 = RequestBody.create(
                    MediaType.parse("multipart/form-data"),  String.valueOf(latitude));
            RequestBody requestBodylong4 = RequestBody.create(
                    MediaType.parse("multipart/form-data"), String.valueOf(longitude));
            RequestBody requestBodyPlantNo = RequestBody.create(
                    MediaType.parse("multipart/form-data"), PlantNo);
            RequestBody requestBodyPlantId = RequestBody.create(
                    MediaType.parse("multipart/form-data"), PlantId);
            RequestBody requestBodyImeiNo = RequestBody.create(
                    MediaType.parse("multipart/form-data"), CustomPrefs.readString(FormTwoActivity.this,FieldStaffApp.USER_IMEI_NO,""));
            RequestBody requestBodyGeotaggingId = RequestBody.create(
                    MediaType.parse("multipart/form-data"), GeotaggingId);
            if (file1 != null && file1.exists()) {
                requestBodyOrderDataFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
                body1 = MultipartBody.Part.createFormData("image_one", file1.getName(), requestBodyOrderDataFile1);
            }
            if (file2 != null && file2.exists()) {
                requestBodyOrderDataFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
                body2 = MultipartBody.Part.createFormData("image_two", file2.getName(), requestBodyOrderDataFile2);
            }
            if (file3 != null && file3.exists()) {
                requestBodyOrderDataFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), file3);
                body3 = MultipartBody.Part.createFormData("image_three", file3.getName(), requestBodyOrderDataFile3);
            }
            if (file4 != null && file4.exists()) {
                requestBodyOrderDataFile4 = RequestBody.create(MediaType.parse("multipart/form-data"), file4);
                body4 = MultipartBody.Part.createFormData("image_four", file4.getName(), requestBodyOrderDataFile4);
            }

            Call<ImageUploadMain> call= RestService.getInstance().restInterface.uploadTreeImages(requestBodyFromApp,
                    requestBodyAppType,requestBodyPageType,requestBodyPlantNo,requestBodyPlantId,requestBodyImeiNo,
                    requestBodylat1,requestBodylong1,requestBodyGeotaggingId,requestBodylat2,requestBodylong2,requestBodylat3,
                    requestBodylong3,requestBodylat4,requestBodylong4,body1,body2,body3,body4);
            call.enqueue(new Callback<ImageUploadMain>() {
                @Override
                public void onResponse(Call<ImageUploadMain> call, Response<ImageUploadMain> response) {
                    if (progressDialog.isShowing())
                    progressDialog.dismiss();
                    if (response.body().getIsSuccess()){
                        showDialogNavigateHone(response.body().getMessage());
                    }
                    else
                        FieldStaffApp.showDialog(FormTwoActivity.this,response.body().getMessage());
                }

                @Override
                public void onFailure(Call<ImageUploadMain> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            });


        }else
        {
            FieldStaffApp.showDialog(FormTwoActivity.this,getResources().getString(R.string.err_msg_internet));
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
    void showDialogNavigateHone(String msg) {
        new android.app.AlertDialog.Builder(FormTwoActivity.this).setMessage(msg).setCancelable(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(FormTwoActivity.this,HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).show();
    }



    private Bitmap resizedBitmap(Bitmap bmp){
        Bitmap resizedBitmap;
        final int maxSize = 500;
        int outWidth;
        int outHeight;
        int inWidth = bmp.getWidth();
        int inHeight = bmp.getHeight();
        if(inWidth > inHeight){
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }
        resizedBitmap = Bitmap.createScaledBitmap(bmp, outWidth, outHeight, false);
        return resizedBitmap;
    }
    private File convertBitmapToFile(Bitmap bmp) throws IOException {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        File f = new File(FormTwoActivity.this.getCacheDir(), "FieldStaffApp"+ts);
        f.createNewFile();

//Convert bitmap to byte array
        Bitmap bitmap = bmp;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }
}
