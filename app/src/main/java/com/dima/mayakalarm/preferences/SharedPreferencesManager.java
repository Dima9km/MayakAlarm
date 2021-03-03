package com.dima.mayakalarm.preferences;

import android.content.SharedPreferences;

import com.dima.mayakalarm.model.Alarm;

import java.util.Locale;


public class SharedPreferencesManager {

    private final String TIME = "time";
    private final String IS_ALARM_ON = "is_alarm_on";
    private final String LANGUAGE = "language";

    private final SharedPreferences preferences;

    public SharedPreferencesManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public Alarm getAlarmClock() {

        return new Alarm(preferences.getLong(TIME, 0),
                preferences.getBoolean(IS_ALARM_ON, false),
                preferences.getString(LANGUAGE, Locale.getDefault().getLanguage()));
    }

    public void updateAlarmClock(Alarm alarm) {
        preferences.edit()
                .putLong(TIME, alarm.getTime())
                .putBoolean(IS_ALARM_ON, alarm.isAlarmOn())
                .putString(LANGUAGE, alarm.getLanguage())
                .apply();
    }
}