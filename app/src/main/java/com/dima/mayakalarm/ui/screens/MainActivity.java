package com.dima.mayakalarm.ui.screens;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.dima.mayakalarm.R;
import com.dima.mayakalarm.model.Alarm;
import com.dima.mayakalarm.repository.Repository;
import com.dima.mayakalarm.util.AlarmHelper;
import com.dima.mayakalarm.util.NotificationHelper;

import java.util.Calendar;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {

    private TextView alarmSetStatus;
    private Button turnAlarmOn;
    private Button turnAlarmOff;
    private Button turnAlarmOnTomorrow;
    private TimePicker timePicker;

    private Alarm alarm;
    private final Calendar calendar = Calendar.getInstance();
    private long time;
    private NotificationHelper notificationHelper;
    private Repository repositoryPrefs;
    private AlarmHelper alarmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        alarmSetStatus = findViewById(R.id.tvAlarmSetStatus);
        turnAlarmOn = findViewById(R.id.btnSetAlarm);
        turnAlarmOff = findViewById(R.id.btnCancelAlarm);
        turnAlarmOnTomorrow = findViewById(R.id.btnOnTomorrow);
        timePicker = findViewById(R.id.tpTime);
        timePicker.setIs24HourView(true);

        repositoryPrefs = new Repository(this);
        notificationHelper = new NotificationHelper(this);
        alarmHelper = new AlarmHelper(this);

        alarm = repositoryPrefs.getAlarmClock();
        time = alarm.getTime();
        updateUI();

        turnAlarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                if (calendar.getTimeInMillis() < currentTimeMillis()) {
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
                }

                time = calendar.getTimeInMillis();
                alarm.setTime(time);
                alarm.setAlarmOn(true);
                repositoryPrefs.updateAlarmClock(alarm);
                alarmHelper.scheduleAlarm(false);
                updateUI();
            }
        });

        turnAlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm.setAlarmOn(false);
                alarm.setTime(time);
                repositoryPrefs.updateAlarmClock(alarm);
                alarmHelper.setAlarmOff();
                updateUI();
            }
        });

        turnAlarmOnTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm.setAlarmOn(true);
                alarm.setTime(time + +(24 * 60 * 60 * 1000));
                repositoryPrefs.updateAlarmClock(alarm);
                alarmHelper.scheduleAlarm(false);
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
        Alarm alarmToShow = repositoryPrefs.getAlarmClock();
        if (!alarmToShow.isAlarmOn()) {
            calendar.setTimeInMillis(alarmToShow.getTime());
            timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(calendar.get(Calendar.MINUTE));
            turnAlarmOn.setVisibility(View.VISIBLE);
            timePicker.setVisibility(View.VISIBLE);
            turnAlarmOff.setVisibility(View.GONE);
            turnAlarmOnTomorrow.setVisibility(View.GONE);
            alarmSetStatus.setText(R.string.alarm_status_off);
            notificationHelper.hide();

        } else {
            turnAlarmOn.setVisibility(View.GONE);
            timePicker.setVisibility(View.GONE);
            turnAlarmOff.setVisibility(View.VISIBLE);
            if (alarmToShow.getTime() < System.currentTimeMillis()) {
                alarmSetStatus.setText("Будильник отложен на 10 минут");
                turnAlarmOnTomorrow.setVisibility(View.VISIBLE);
            } else {
                alarmSetStatus.setText(String.format(getResources()
                        .getString(R.string.alarm_status_on), DateFormat
                        .format("HH.mm\n EEEE, dd MMMM yyyy", alarmToShow.getTime()).toString()));
                turnAlarmOnTomorrow.setVisibility(View.GONE);
            }
            notificationHelper.show();
        }
    }
}