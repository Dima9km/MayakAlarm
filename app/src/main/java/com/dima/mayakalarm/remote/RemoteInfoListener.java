package com.dima.mayakalarm.remote;

import com.dima.mayakalarm.model.InfoToShow;

public interface RemoteInfoListener {
    void onGetData(int temp, int humidity, String description, int windSpeed);

    void onGetImageData(InfoToShow infoToShow);

    void onError(String message);
}
