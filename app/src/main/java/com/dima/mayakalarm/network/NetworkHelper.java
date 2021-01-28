package com.dima.mayakalarm.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {
    public static NetworkHelper retrofitInstance;
    private final static String BASE_WEATHER_URL = "http://api.openweathermap.org/";
    private final static String BASE_IMAGE_URL = "https://loremflickr.com/json/";
    public Retrofit retrofitWeather;
    public Retrofit retrofitImage;

    public NetworkHelper() {
        Gson gson = new GsonBuilder().setLenient().create();

        retrofitWeather = new Retrofit.Builder()
                .baseUrl(BASE_WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitImage = new Retrofit.Builder()
                .baseUrl(BASE_IMAGE_URL)
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


