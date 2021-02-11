package com.dima.mayakalarm.repository;

import android.content.Context;
import android.preference.PreferenceManager;

import com.dima.mayakalarm.model.Alarm;
import com.dima.mayakalarm.model.InfoToShow;
import com.dima.mayakalarm.preferences.SharedPreferencesManager;
import com.dima.mayakalarm.remote.RemoteInfoDownloader;
import com.dima.mayakalarm.remote.RemoteInfoListener;

public class Repository {

    private RepositoryListener repositoryListener;
    private Context context;
    private SharedPreferencesManager sharedPreferencesManager;

    public Repository(Context context) {
        this.context = context;
        sharedPreferencesManager = new SharedPreferencesManager(PreferenceManager.getDefaultSharedPreferences(context));
    }

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

    public Alarm getAlarmClock() {
        return sharedPreferencesManager.getAlarmClock();
    }

    public void updateAlarmClock(Alarm alarm) {
        sharedPreferencesManager.updateAlarmClock(alarm);
    }
}
