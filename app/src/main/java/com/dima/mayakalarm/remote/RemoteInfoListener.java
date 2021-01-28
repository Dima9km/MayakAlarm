package com.dima.mayakalarm.remote;

import com.dima.mayakalarm.model.InfoToShow;

public interface RemoteInfoListener {
    void onGetData(InfoToShow infoToShow);

    void onError(String message);
}
