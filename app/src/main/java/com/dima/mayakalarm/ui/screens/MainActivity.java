package com.dima.mayakalarm.ui.screens;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.dima.mayakalarm.R;
import com.dima.mayakalarm.model.Alarm;
import com.dima.mayakalarm.repository.Repository;
import com.dima.mayakalarm.util.NotificationHelper;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView alarmSetStatus;
    private Button turnAlarmOn;
    private Button turnAlarmOff;
    private TimePicker timePicker;

    private Alarm alarm;
    private final Calendar calendar = Calendar.getInstance();
    private final NotificationHelper notificationHelper = new NotificationHelper();
    private NotificationManager notificationManager;
    private Notification notification;
    private final Repository repository = new Repository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        alarmSetStatus = findViewById(R.id.tvAlarmSetStatus);
        turnAlarmOn = findViewById(R.id.btnSetAlarm);
        turnAlarmOff = findViewById(R.id.btnCancelAlarm);
        timePicker = findViewById(R.id.tpTime);
        timePicker.setIs24HourView(true);

        repository.setPreferences(PreferenceManager.getDefaultSharedPreferences(this));

        alarm = repository.getAlarmClock();

        notificationManager = notificationHelper.getManager(this);
        notification = notificationHelper.getNotification(this);

        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        updateUI();

        turnAlarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm = new Alarm(timePicker.getHour(), timePicker.getMinute(), true);

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

                alarm.setAlarm(getApplicationContext());
                alarm.setAlarmOn(true);

                repository.updateAlarmClock(alarm);
                updateUI();
            }
        });

        turnAlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm.setAlarmOn(false);
                repository.updateAlarmClock(alarm);
                updateUI();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (!alarm.isAlarmOn()) {
            timePicker.setHour(alarm.getHour());
            timePicker.setMinute(alarm.getMinute());
            turnAlarmOn.setVisibility(View.VISIBLE);
            timePicker.setVisibility(View.VISIBLE);
            turnAlarmOff.setVisibility(View.GONE);
            alarmSetStatus.setText(R.string.alarm_status_off);
            notificationManager.cancel(1);

        } else {
            turnAlarmOn.setVisibility(View.GONE);
            timePicker.setVisibility(View.GONE);
            turnAlarmOff.setVisibility(View.VISIBLE);
            alarmSetStatus.setText(String.format(getResources()
                    .getString(R.string.alarm_status_on), DateFormat
                    .format("HH.mm", calendar).toString()));
            notificationManager.notify(1, notification);
        }
    }
}