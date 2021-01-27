package com.dima.mayakalarm.repository;

import com.dima.mayakalarm.remote.RemoteInfoDownloader;
import com.dima.mayakalarm.remote.RemoteInfoListener;

public class Repository {

    private RepositoryListener repositoryListener;

    public Repository(RepositoryListener repositoryListener) {
        this.repositoryListener = repositoryListener;
    }

    public void getRemoteInfo() {
        repositoryListener.onStartDownload();

        new RemoteInfoDownloader().getRemoteWeather(new RemoteInfoListener() {

            @Override
            public void onGetData(String currentWeather) {
                repositoryListener.onGetRemoteInfo(currentWeather);
                repositoryListener.onEndDownload();
            }

            @Override
            public void onError(String message) {
                repositoryListener.onError(message);
            }
        });
    }
}
