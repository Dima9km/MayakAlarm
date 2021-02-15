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

public class MainActivity extends AppCompatActivity {

    private TextView alarmSetStatus;
    private Button turnAlarmOn;
    private Button turnAlarmOff;
    private Button turnAlarmOnTomorrow;
    private TimePicker timePicker;

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

        updateUI();

        turnAlarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmHelper.scheduleAlarm(timePicker.getHour(), timePicker.getMinute());
                updateUI();
            }
        });

        turnAlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmHelper.setAlarmOff();
                updateUI();
            }
        });

        turnAlarmOnTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmHelper.scheduleNextDayAlarm();
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
            Calendar calendar = Calendar.getInstance();
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
                alarmSetStatus.setText(String.format(getResources()
                        .getString(R.string.alarm_status_on), DateFormat
                        .format("HH.mm\n EEEE, dd MMMM yyyy", alarmToShow.getTime() + 10 * 60 * 1000).toString()));
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