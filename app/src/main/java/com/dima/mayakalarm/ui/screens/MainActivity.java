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
    private TimePicker timePicker;

    private Alarm alarm;
    private long time;
    private final Calendar calendar = Calendar.getInstance();
    private NotificationHelper notificationHelper;
    private Repository repository;
    private AlarmHelper alarmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        alarmSetStatus = findViewById(R.id.tvAlarmSetStatus);
        turnAlarmOn = findViewById(R.id.btnSetAlarm);
        turnAlarmOff = findViewById(R.id.btnCancelAlarm);
        timePicker = findViewById(R.id.tpTime);
        timePicker.setIs24HourView(true);

        repository = new Repository(this);
        notificationHelper = new NotificationHelper(this);
        alarmHelper = new AlarmHelper(this);

        alarm = repository.getAlarmClock();
        time = alarm.getTime();

        updateUI();

        turnAlarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
                }

                time = calendar.getTimeInMillis();
                alarm.setTime(time);
                alarm.setAlarmOn(true);
                repository.updateAlarmClock(alarm);
                alarmHelper.setAlarm(false);
                updateUI();
            }
        });

        turnAlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm.setAlarmOn(false);
                alarm.setTime(time - 60 * 60 * 1000);
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
            timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(calendar.get(Calendar.MINUTE));
            turnAlarmOn.setVisibility(View.VISIBLE);
            timePicker.setVisibility(View.VISIBLE);
            turnAlarmOff.setVisibility(View.GONE);
            alarmSetStatus.setText(R.string.alarm_status_off);
            notificationHelper.hide();

        } else {
            turnAlarmOn.setVisibility(View.GONE);
            timePicker.setVisibility(View.GONE);
            turnAlarmOff.setVisibility(View.VISIBLE);
            alarmSetStatus.setText(String.format(getResources()
                    .getString(R.string.alarm_status_on), DateFormat
                    .format("HH.mm\n EEEE, dd MMMM yyyy", time).toString()));
            notificationHelper.show();
        }
    }
}