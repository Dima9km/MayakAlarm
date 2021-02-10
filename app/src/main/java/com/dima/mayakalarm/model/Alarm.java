package com.dima.mayakalarm.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.dima.mayakalarm.R;
import com.dima.mayakalarm.util.AlarmBroadcastReceiver;

import java.util.Calendar;

public class Alarm {

    private int hour;
    private int minute;
    private boolean isAlarmOn;
    private boolean isAlarmSetOnNextDay;

    public Alarm(int hour, int minute, boolean isAlarmOn, boolean isAlarmSetOnNextDay) {
        this.hour = hour;
        this.minute = minute;
        this.isAlarmOn = isAlarmOn;
        this.isAlarmSetOnNextDay = isAlarmSetOnNextDay;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isAlarmOn() {
        return isAlarmOn;
    }

    public boolean isAlarmSetOnNextDay() {
        return isAlarmSetOnNextDay;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setAlarmOn(boolean isAlarmOn) {
        this.isAlarmOn = isAlarmOn;
    }

    public void setAlarmSetOnNextDay(boolean isAlarmSetOnNextDay) {
        this.isAlarmSetOnNextDay = isAlarmSetOnNextDay;
    }

    public void setAlarm(Context context, Calendar calendar) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );

        String toastText = String.format(context.getResources()
                .getString(R.string.alarm_status_on), DateFormat
                .format("HH.mm\n EEEE, dd MMMM yyyy", calendar).toString());
        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

    }
}
