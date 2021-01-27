package com.dima.mayakalarm.ui.screens;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.dima.mayakalarm.R;
import com.dima.mayakalarm.model.Alarm;
import com.dima.mayakalarm.preferences.SharedPreferencesManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button alarmOn;
    private Button alarmOff;
    private TimePicker timePicker;
    private Alarm alarm;
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmOn = findViewById(R.id.btnSetAlarm);
        alarmOff = findViewById(R.id.btnCancelAlarm);
        timePicker = findViewById(R.id.tpTime);
        timePicker.setIs24HourView(true);

        Calendar calendar = Calendar.getInstance();

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentText("Alarm on!")
                .setSmallIcon(R.drawable.ic_alarm_notification)
                .build();

        sharedPreferencesManager = new SharedPreferencesManager(PreferenceManager.getDefaultSharedPreferences(this));

        alarm = sharedPreferencesManager.getAlarmClock();

        if (alarm.isAlarmOn()) {
            notificationManager.notify(1, notification);
            timePicker.setHour(alarm.getHour());
            timePicker.setMinute(alarm.getMinute());
        }


        alarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm = new Alarm(timePicker.getHour(), timePicker.getMinute(), true);
                sharedPreferencesManager.updateAlarmClock(alarm);

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

                Toast.makeText(getApplicationContext(), "Будильник зазвучит в " + DateFormat.format("HH.mm", calendar).toString(), Toast.LENGTH_SHORT).show();

                alarm.setAlarm(getApplicationContext());
                alarm.setAlarmOn(true);

                notificationManager.notify(1, notification);
            }
        });

        alarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm.setAlarmOn(false);
                notificationManager.cancel(1);
                sharedPreferencesManager.updateAlarmClock(alarm);
            }
        });
    }
}