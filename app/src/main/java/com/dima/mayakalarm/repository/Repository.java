package com.dima.mayakalarm.repository;

import com.dima.mayakalarm.model.InfoToShow;
import com.dima.mayakalarm.remote.RemoteInfoDownloader;
import com.dima.mayakalarm.remote.RemoteInfoListener;

public class Repository {

    private RepositoryListener repositoryListener;

    public Repository(RepositoryListener repositoryListener) {
        this.repositoryListener = repositoryListener;
    }

    public void getInfoToShow() {
        repositoryListener.onStartDownload();

        new RemoteInfoDownloader().getInfoToShow(new RemoteInfoListener() {

            @Override
            public void onGetData(InfoToShow infoToShow) {
                repositoryListener.onGetRemoteInfo(infoToShow);
                repositoryListener.onEndDownload();
            }

            @Override
            public void onError(String message) {
                repositoryListener.onError(message);
            }
        });
    }
}
