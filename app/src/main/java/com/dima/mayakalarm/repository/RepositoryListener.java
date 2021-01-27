package com.dima.mayakalarm.repository;

public interface RepositoryListener {

    void onStartDownload();

    void onGetRemoteInfo(String currentWeather);

    void onError(String message);

    void onEndDownload();
}
