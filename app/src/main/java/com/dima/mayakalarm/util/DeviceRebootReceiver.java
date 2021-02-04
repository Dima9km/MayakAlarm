package com.dima.mayakalarm.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.dima.mayakalarm.model.Alarm;
import com.dima.mayakalarm.repository.Repository;

public class DeviceRebootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Repository repository = new Repository();
        NotificationHelper notificationHelper = new NotificationHelper();
        NotificationManager notificationManager = notificationHelper.getManager(context);
        Notification notification = notificationHelper.getNotification(context);

        repository.setPreferences(PreferenceManager.getDefaultSharedPreferences(context));
        Alarm alarm = repository.getAlarmClock();
        if (alarm.isAlarmOn()) {
            Toast.makeText(context, alarm.getHour() + "." + alarm
                    .getMinute(), Toast.LENGTH_LONG).show();
            alarm.setAlarm(context);
            notificationManager.notify(1, notification);
        }
    }
}
