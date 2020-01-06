package com.example.instorage_caller.retrofit;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://staging.instorage.se:8080/api/"; //live server https


    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor ();
// set your desired log level
            logging.setLevel (HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient
                    .Builder ()
                    .connectTimeout (60, TimeUnit.SECONDS)
                    .readTimeout (60, TimeUnit.SECONDS)
                    .addNetworkInterceptor (new StethoInterceptor())
                    .writeTimeout (600, TimeUnit.SECONDS);

// add your other interceptors …


// add logging as last interceptor
            httpClient.addInterceptor (logging).addNetworkInterceptor(new StethoInterceptor());  // <-

            Gson gson = new GsonBuilder()
                    .setDateFormat ("yyyy-MM-dd HH:mm:ss")
                    .setLenient ()
                    .create ();

            retrofit = new Retrofit.Builder ()
                    .baseUrl (BASE_URL)
                    .addConverterFactory (GsonConverterFactory.create (gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client (httpClient.build ())
                    .build ();
        }
        return retrofit;
    }
}
