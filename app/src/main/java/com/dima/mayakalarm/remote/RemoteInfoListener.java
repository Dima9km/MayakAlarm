package com.dima.mayakalarm.remote;

public interface RemoteInfoListener {
    void onGetData(int temp, int humidity, String description, int windSpeed);

    void onGetImageData(String picUrl);

    void onError(String message);
}
