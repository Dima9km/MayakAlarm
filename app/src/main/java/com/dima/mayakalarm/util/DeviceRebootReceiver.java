package com.dima.mayakalarm.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.dima.mayakalarm.model.Alarm;
import com.dima.mayakalarm.repository.Repository;

import java.util.Calendar;

public class DeviceRebootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Repository repository = new Repository();
        NotificationHelper notificationHelper = new NotificationHelper(context);

        repository.setPreferences(PreferenceManager.getDefaultSharedPreferences(context));
        Alarm alarm = repository.getAlarmClock();
        if (alarm.isAlarmOn()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            if (alarm.isAlarmSetOnNextDay()) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
            }

            alarm.setAlarm(context, calendar);

            notificationHelper.show();
        }
    }
}
