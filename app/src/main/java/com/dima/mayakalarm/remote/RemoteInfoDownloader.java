package com.dima.mayakalarm.remote;

import androidx.annotation.NonNull;

import com.dima.mayakalarm.model.FlikrImage;
import com.dima.mayakalarm.model.WeatherResponse;
import com.dima.mayakalarm.network.ImageApi;
import com.dima.mayakalarm.network.NetworkHelper;
import com.dima.mayakalarm.network.WeatherApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteInfoDownloader {

    public void getInfo(RemoteInfoListener remoteInfoListener, String lang) {
        WeatherApi weatherApi = NetworkHelper.getInstance().retrofitWeather.create(WeatherApi.class);
        String units = "metric";
        String lon = "39.17";
        String lat = "51.6664";
        String appId = "f52cbda5d0f59d7559eb126d5fc0e0e2";
        Call<WeatherResponse> callWeather = weatherApi.getWeatherData(lat, lon, appId, units, lang);

        callWeather.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                    getRemoteImage(remoteInfoListener);

                    remoteInfoListener.onGetData(weatherResponse.main.temp,
                            weatherResponse.main.humidity,
                            weatherResponse.weather.get(0).description,
                            weatherResponse.wind.speed);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                remoteInfoListener.onError(t.getMessage());
            }
        });
    }

    public void getRemoteImage(RemoteInfoListener remoteInfoListener) {
        ImageApi imageApi = NetworkHelper.getInstance().retrofitImage.create(ImageApi.class);
        Call<FlikrImage> callImage = imageApi.getImageUrl();
        callImage.enqueue(new Callback<FlikrImage>() {
            @Override
            public void onResponse(@NonNull Call<FlikrImage> call, @NonNull Response<FlikrImage> response) {
                if (response.code() == 200) {
                    FlikrImage flikrImage = response.body();
                    String picUrl = flikrImage.getFile();
                    remoteInfoListener.onGetImageData(picUrl);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FlikrImage> call, @NonNull Throwable t) {
                remoteInfoListener.onError(t.getMessage());
            }
        });
    }
}

