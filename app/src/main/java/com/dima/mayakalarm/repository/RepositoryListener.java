package com.dima.mayakalarm.repository;

import com.dima.mayakalarm.model.InfoToShow;

public interface RepositoryListener {

    void onStartDownload();

    void onGetRemoteInfo(int temp, int humidity, String description, int windSpeed);

    void onGetImageInfo(InfoToShow infoToShow);

    void onError(String message);

    void onEndDownload();
}
