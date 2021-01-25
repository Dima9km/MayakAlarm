package com.dima.mayakalarm.remote;

import androidx.annotation.NonNull;

import com.dima.mayakalarm.model.WeatherResponse;
import com.dima.mayakalarm.network.NetworkHelper;
import com.dima.mayakalarm.network.WeatherApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteInfoDownloader {

    private String currentWeather;

    public String getRemoteWeather() {

        final String AppId = "f52cbda5d0f59d7559eb126d5fc0e0e2";
        final String lat = "51.6664";
        final String lon = "39.17";
        final String units = "metric";
        final String lang = "ru";

        WeatherApi weatherApi = NetworkHelper.getInstance().retrofit.create(WeatherApi.class);
        Call<WeatherResponse> call = weatherApi.getCurrentWeatherData(lat, lon, AppId, units, lang);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    currentWeather =
                            "Температура: " +
                                    weatherResponse.main.temp + " C" +
                                    "\n" +
                                    "Влажность: " +
                                    weatherResponse.main.humidity + " %" +
                                    "\n" +
                                    "Чо как: пока " +
                                    weatherResponse.weather.get(0).description +
                                    "\n" +
                                    "Ветерок: " +
                                    weatherResponse.wind.speed + " м/с";

                    String temp = currentWeather;
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                currentWeather = t.getMessage();
            }
        });
        return currentWeather;
    }
}

