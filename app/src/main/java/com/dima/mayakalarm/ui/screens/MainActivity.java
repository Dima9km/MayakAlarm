package com.dima.mayakalarm.ui.screens;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.dima.mayakalarm.R;
import com.dima.mayakalarm.util.AlarmHelper;
import com.dima.mayakalarm.util.LocaleManager;
import com.dima.mayakalarm.util.NotificationHelper;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView tvAlarmStatus;
    private Button btnChangeLocale;
    private Button btnSetAlarm;
    private Button btnCancelAlarm;
    private Button btnOnTomorrow;
    private TimePicker tpTime;

    private NotificationHelper notificationHelper;
    private AlarmHelper alarmHelper;
    private LocaleManager localeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationHelper = new NotificationHelper(this);
        alarmHelper = new AlarmHelper(this);
        localeManager = new LocaleManager(this);

        tvAlarmStatus = findViewById(R.id.tvAlarmSetStatus);
        btnChangeLocale = findViewById(R.id.btnChangeLocale);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        btnCancelAlarm = findViewById(R.id.btnCancelAlarm);
        btnOnTomorrow = findViewById(R.id.btnOnTomorrow);
        tpTime = findViewById(R.id.tpTime);
        tpTime.setIs24HourView(true);

        btnChangeLocale.setOnClickListener(v -> {
            localeManager.switchLocale();
            updateUI();
            recreate();
        });

        btnSetAlarm.setOnClickListener(v -> {
            alarmHelper.scheduleAlarm(tpTime.getHour(), tpTime.getMinute());
            updateUI();
        });

        btnCancelAlarm.setOnClickListener(v -> {
            alarmHelper.setAlarmOff();
            updateUI();
        });

        btnOnTomorrow.setOnClickListener(v -> {
            alarmHelper.scheduleNextDayAlarm();
            updateUI();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        long time = alarmHelper.getAlarm().getTime();
        btnChangeLocale.setText(localeManager.getCurrentLocale());

        if (!alarmHelper.getAlarm().isAlarmOn()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);

            tpTime.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            tpTime.setMinute(calendar.get(Calendar.MINUTE));
            tpTime.setVisibility(View.VISIBLE);

            btnSetAlarm.setVisibility(View.VISIBLE);
            btnCancelAlarm.setVisibility(View.GONE);
            btnOnTomorrow.setVisibility(View.GONE);
            tvAlarmStatus.setText(R.string.alarm_status_off);

            notificationHelper.hide();

        } else {
            btnSetAlarm.setVisibility(View.GONE);
            tpTime.setVisibility(View.GONE);
            btnCancelAlarm.setVisibility(View.VISIBLE);
            if (time < System.currentTimeMillis()) {
                tvAlarmStatus.setText(String.format(getResources()
                        .getString(R.string.alarm_status_on), DateFormat
                        .format("HH.mm\n EEEE, dd MMMM yyyy", time + 10 * 60 * 1000).toString()));
                btnOnTomorrow.setVisibility(View.VISIBLE);
            } else {
                tvAlarmStatus.setText(String.format(getResources()
                        .getString(R.string.alarm_status_on), DateFormat
                        .format("HH.mm\n EEEE, dd MMMM yyyy", time).toString()));
                btnOnTomorrow.setVisibility(View.GONE);
            }

            notificationHelper.show();
        }
    }
}