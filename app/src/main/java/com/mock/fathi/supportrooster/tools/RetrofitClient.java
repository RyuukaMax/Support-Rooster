package com.mock.fathi.supportrooster.tools;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {
    private static Retrofit retroClient = null;
    private static final String BASE_URL = "https://private-22cae9-fathiasbi.apiary-mock.com";

    // Singleton client
    private RetrofitClient() {
        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        retroClient = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(rxAdapter)
                .client(client)
                .build();
    }

    public static Retrofit getRetroClient() {
        if (retroClient == null)
            new RetrofitClient();

        return retroClient;
    }
}