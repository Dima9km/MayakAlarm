package com.dima.mayakalarm.remote;

public interface RemoteInfoListener {
    void onGetData(String currentWeather);

    void onError(String message);
}
