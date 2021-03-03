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
import com.dima.mayakalarm.util.LanguageSwitcher;
import com.dima.mayakalarm.util.NotificationHelper;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView tvAlarmStatus;
    private Button btnSetLang;
    private Button btnSetAlarm;
    private Button btnCancelAlarm;
    private Button btnOnTomorrow;
    private TimePicker tpTime;

    private NotificationHelper notificationHelper;
    private AlarmHelper alarmHelper;
    private LanguageSwitcher languageSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationHelper = new NotificationHelper(this);
        alarmHelper = new AlarmHelper(this);
        languageSwitcher = new LanguageSwitcher(this);

        setContentView(R.layout.activity_main);
        tvAlarmStatus = findViewById(R.id.tvAlarmSetStatus);
        btnSetLang = findViewById(R.id.btnSetLang);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        btnCancelAlarm = findViewById(R.id.btnCancelAlarm);
        btnOnTomorrow = findViewById(R.id.btnOnTomorrow);
        tpTime = findViewById(R.id.tpTime);
        tpTime.setIs24HourView(true);

        btnSetLang.setOnClickListener(v -> {
            languageSwitcher.switchLanguage();
            recreate();
            updateUI();
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
        btnSetLang.setText(alarmHelper.getAlarm().getLanguage());

        if (!alarmHelper.getAlarm().isAlarmOn()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            tpTime.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            tpTime.setMinute(calendar.get(Calendar.MINUTE));
            btnSetAlarm.setVisibility(View.VISIBLE);
            tpTime.setVisibility(View.VISIBLE);
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