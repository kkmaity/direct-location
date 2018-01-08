package com.fieldstaffapp.fieldstaffapp.restservice;


import com.fieldstaffapp.fieldstaffapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {

    private static RestService restService;
    public RestInterface restInterface;
    private static final int CONNECT_TIMEOUT_MILLIS = 2 * 60; // 2 minute
    private static final int READ_TIMEOUT_MILLIS = 2 * 60;
    /*private String currentDateTimeString;
    private static final String TAG = "RestService";*/

    public RestService() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(httpLoggingInterceptor);
        }

        /*GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new DeviceEventTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new DateTypeAdapterFactory());*/

        restInterface = new Retrofit.Builder()
                .baseUrl(RestInterface.BASE_URL)
                .client(clientBuilder.retryOnConnectionFailure(true)
                        .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.SECONDS)
                        .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RestInterface.class);
        //currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    }

    public static RestService getInstance() {
        if (restService == null) {
            restService = new RestService();
        }

        return restService;
    }
}
