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

import java.util.Calendar;

import static java.lang.System.currentTimeMillis;

public class AlarmHelper {

    private final Context context;
    private final AlarmManager alarmManager;
    private final Repository repository;
    private final PendingIntent pendingIntent;
    private final Alarm alarm;
    private final Calendar calendar = Calendar.getInstance();

    public AlarmHelper(Context context) {
        this.context = context;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        repository = new Repository(context);
        alarm = repository.getAlarmClock();

        pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmBroadcastReceiver.class), 0);
    }

    public Alarm getAlarm() {
        return repository.getAlarmClock();
    }

    public void scheduleAlarm(int hour, int minute) {
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() < currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        long time = calendar.getTimeInMillis();

        alarm.setAlarmOn(true);
        alarm.setTime(time);
        repository.updateAlarmClock(alarm);
        scheduleAlarmManager(time);
    }

    public void scheduleSnoozedAlarm() {
        scheduleAlarmManager(alarm.getTime() + 10 * 60 * 1000);
    }

    public void scheduleNextDayAlarm() {
        long time = alarm.getTime() + (24 * 60 * 60 * 1000);
        alarm.setTime(time);
        alarm.setAlarmOn(true);
        repository.updateAlarmClock(alarm);
        scheduleAlarmManager(time);
    }

    public void scheduleCurrentAlarm() {
        scheduleAlarmManager(alarm.getTime());
    }

    public void setAlarmOff() {
        alarmManager.cancel(pendingIntent);
        alarm.setAlarmOn(false);
        repository.updateAlarmClock(alarm);
        Toast.makeText(context, R.string.alarm_is_off, Toast.LENGTH_LONG).show();
    }

    private void scheduleAlarmManager(long time) {
        if (alarm.isAlarmOn()) {
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
}
