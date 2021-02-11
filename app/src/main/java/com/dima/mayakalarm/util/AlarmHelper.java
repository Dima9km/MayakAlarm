package com.dima.mayakalarm.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.dima.mayakalarm.R;
import com.dima.mayakalarm.model.Alarm;
import com.dima.mayakalarm.repository.Repository;

public class AlarmHelper {

    private final Context context;

    public AlarmHelper(Context context) {
        this.context = context;
    }

    public void setAlarm(boolean isAlarmSnoozed) {

        Repository repository = new Repository(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Alarm alarm = repository.getAlarmClock();
        long timeToSet = alarm.getTime();
        long time;

        if (isAlarmSnoozed) {
            time = System.currentTimeMillis() + 10 * 60 * 1000;
        } else {
            time = timeToSet;
        }

        alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
        );

        String toastText = String.format(context.getResources()
                .getString(R.string.alarm_status_on), DateFormat
                .format("HH.mm\n EEEE, dd MMMM yyyy", time).toString());
        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

    }
}
