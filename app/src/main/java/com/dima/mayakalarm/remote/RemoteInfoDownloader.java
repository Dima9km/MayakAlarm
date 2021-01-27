package com.dima.mayakalarm.remote;

import androidx.annotation.NonNull;

import com.dima.mayakalarm.model.MyAlarmInfo;
import com.dima.mayakalarm.network.NetworkHelper;
import com.dima.mayakalarm.network.WeatherApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteInfoDownloader {


    public void getRemoteWeather(RemoteInfoListener remoteInfoListener) {

        final String AppId = "f52cbda5d0f59d7559eb126d5fc0e0e2";
        final String lat = "51.6664";
        final String lon = "39.17";
        final String units = "metric";
        final String lang = "ru";

        WeatherApi weatherApi = NetworkHelper.getInstance().retrofit.create(WeatherApi.class);
        Call<MyAlarmInfo> call = weatherApi.getCurrentWeatherData(lat, lon, AppId, units, lang);
        call.enqueue(new Callback<MyAlarmInfo>() {
            @Override
            public void onResponse(@NonNull Call<MyAlarmInfo> call, @NonNull Response<MyAlarmInfo> response) {
                if (response.code() == 200) {
                    MyAlarmInfo myAlarmInfo = response.body();
                    assert myAlarmInfo != null;

                    String currentWeather =
                            "Температура: " +
                                    myAlarmInfo.main.temp + " C" +
                                    "\n" +
                                    "Влажность: " +
                                    myAlarmInfo.main.humidity + " %" +
                                    "\n" +
                                    "Чо как: пока " +
                                    myAlarmInfo.weather.get(0).description +
                                    "\n" +
                                    "Ветерок: " +
                                    myAlarmInfo.wind.speed + " м/с";

                    remoteInfoListener.onGetData(currentWeather);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyAlarmInfo> call, @NonNull Throwable t) {
                remoteInfoListener.onError(t.getMessage());
            }
        });
    }
}

