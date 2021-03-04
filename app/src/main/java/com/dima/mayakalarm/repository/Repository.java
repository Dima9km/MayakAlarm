package com.dima.mayakalarm.repository;

import android.content.Context;
import android.preference.PreferenceManager;

import com.dima.mayakalarm.model.Alarm;
import com.dima.mayakalarm.model.InfoToShow;
import com.dima.mayakalarm.preferences.SharedPreferencesManager;
import com.dima.mayakalarm.remote.RemoteInfoDownloader;
import com.dima.mayakalarm.remote.RemoteInfoListener;

public class Repository {

    private final SharedPreferencesManager sharedPreferencesManager;

    public Repository(Context context) {
        sharedPreferencesManager = new SharedPreferencesManager(PreferenceManager.getDefaultSharedPreferences(context));
    }

    public void getInfoToShow(RepositoryListener repositoryListener) {
        repositoryListener.onStartDownload();

        new RemoteInfoDownloader().getInfoToShow(new RemoteInfoListener() {

            @Override
            public void onGetData(int temp, int humidity, String description, int windSpeed) {
                repositoryListener.onGetRemoteInfo(temp, humidity, description, windSpeed);
                repositoryListener.onEndDownload();
            }

            @Override
            public void onGetImageData(InfoToShow infoToShow) {
                repositoryListener.onGetImageInfo(infoToShow);
            }

            @Override
            public void onError(String message) {
                repositoryListener.onError(message);
            }
        }, getCurrentLanguage());
    }

    public Alarm getAlarmClock() {
        return sharedPreferencesManager.getAlarmClock();
    }

    public void updateAlarmClock(Alarm alarm) {
        sharedPreferencesManager.updateAlarmClock(alarm);
    }

    public String getCurrentLanguage() {
        return sharedPreferencesManager.getAlarmClock().getLanguage();
    }
}
