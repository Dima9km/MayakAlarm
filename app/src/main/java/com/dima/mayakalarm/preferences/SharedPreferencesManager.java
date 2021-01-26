package com.dima.mayakalarm.preferences;

import android.content.SharedPreferences;

import com.dima.mayakalarm.model.Alarm;


public class SharedPreferencesManager {

    private final String HOUR = "hour";
    private final String MINUTE = "minute";
    private final String IS_ALARM_ON = "is_alarm_on";

    private SharedPreferences preferences;

    public SharedPreferencesManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public Alarm getAlarmClock() {
        return new Alarm(preferences.getInt(HOUR, 0),
                preferences.getInt(MINUTE, 0),
                preferences.getBoolean(IS_ALARM_ON, false));
    }

    public void updateAlarmClock(Alarm alarm) {
        preferences.edit()
                .putInt(HOUR, alarm.getHour())
                .putInt(MINUTE, alarm.getMinute())
                .putBoolean(IS_ALARM_ON, alarm.isAlarmOn())
                .apply();
    }
}