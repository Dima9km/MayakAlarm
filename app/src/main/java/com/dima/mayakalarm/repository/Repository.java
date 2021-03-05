package com.dima.mayakalarm.repository;

import android.content.Context;
import android.preference.PreferenceManager;

import com.dima.mayakalarm.model.Alarm;
import com.dima.mayakalarm.preferences.SharedPreferencesManager;
import com.dima.mayakalarm.remote.RemoteInfoDownloader;
import com.dima.mayakalarm.remote.RemoteInfoListener;

public class Repository {

    private static Repository repository;
    private final SharedPreferencesManager sharedPreferencesManager;

    private Repository(Context context) {
        sharedPreferencesManager = new SharedPreferencesManager(PreferenceManager.getDefaultSharedPreferences(context));
    }

    public static Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
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
            public void onGetImageData(String picUrl) {
                repositoryListener.onGetImageInfo(picUrl);
            }

            @Override
            public void onError(String message) {
                repositoryListener.onError(message);
            }
        }, getAppLocale());
    }

    public Alarm getAlarmClock() {
        return sharedPreferencesManager.getAlarmClock();
    }

    public void updateAlarmClock(Alarm alarm) {
        sharedPreferencesManager.updateAlarmClock(alarm);
    }

    public String getAppLocale() {
        return sharedPreferencesManager.getLocale();
    }

    public void saveAppLocale(String locale) {
        sharedPreferencesManager.updateLocale(locale);
    }

}
