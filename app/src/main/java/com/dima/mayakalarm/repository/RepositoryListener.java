package com.dima.mayakalarm.repository;

public interface RepositoryListener {

    void onStartDownload();

    void onGetRemoteInfo(int temp, int humidity, String description, int windSpeed);

    void onGetImageInfo(String picUrl);

    void onError();

    void onEndDownload();
}
