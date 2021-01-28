package com.dima.mayakalarm.remote;

import androidx.annotation.NonNull;

import com.dima.mayakalarm.model.FlikrImage;
import com.dima.mayakalarm.model.InfoToShow;
import com.dima.mayakalarm.model.WeatherResponse;
import com.dima.mayakalarm.network.ImageApi;
import com.dima.mayakalarm.network.NetworkHelper;
import com.dima.mayakalarm.network.WeatherApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteInfoDownloader {

    final String AppId = "f52cbda5d0f59d7559eb126d5fc0e0e2";
    final String lat = "51.6664";
    final String lon = "39.17";
    final String units = "metric";
    final String lang = "ru";

    final InfoToShow infoToShow = new InfoToShow();

    WeatherApi weatherApi = NetworkHelper.getInstance().retrofitWeather.create(WeatherApi.class);
    Call<WeatherResponse> callWeather = weatherApi.getWeatherData(lat, lon, AppId, units, lang);

    ImageApi imageApi = NetworkHelper.getInstance().retrofitImage.create(ImageApi.class);
    Call<FlikrImage> callImage = imageApi.getImageUrl();

    public void getInfoToShow(RemoteInfoListener remoteInfoListener) {

        callWeather.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                    getRemoteImage(remoteInfoListener);
                    String currentWeather =
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

                    infoToShow.setCurrentWeather(currentWeather);


                    remoteInfoListener.onGetData(infoToShow);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                remoteInfoListener.onError(t.getMessage());
            }
        });
    }

    public void getRemoteImage(RemoteInfoListener remoteInfoListener) {

        callImage.enqueue(new Callback<FlikrImage>() {
            @Override
            public void onResponse(@NonNull Call<FlikrImage> call, @NonNull Response<FlikrImage> response) {
                if (response.code() == 200) {
                    FlikrImage flikrImage = response.body();
                    infoToShow.setImageUrl(flikrImage.getFile());
                    remoteInfoListener.onGetData(infoToShow);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FlikrImage> call, @NonNull Throwable t) {
                remoteInfoListener.onError(t.getMessage());
            }
        });
    }
}

