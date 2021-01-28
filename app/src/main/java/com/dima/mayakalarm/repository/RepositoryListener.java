package com.dima.mayakalarm.repository;

import com.dima.mayakalarm.model.InfoToShow;

public interface RepositoryListener {

    void onStartDownload();

    void onGetRemoteInfo(InfoToShow infoToShow);

    void onError(String message);

    void onEndDownload();
}
