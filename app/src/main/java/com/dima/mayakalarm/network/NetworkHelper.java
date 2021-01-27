package com.dima.mayakalarm.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {
    public static NetworkHelper retrofitInstance;
    private final static String baseUrl = "http://api.openweathermap.org/";
    public Retrofit retrofit;

    public NetworkHelper() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static NetworkHelper getInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new NetworkHelper();
        }
        return retrofitInstance;
    }
}


