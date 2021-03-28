package com.dima.mayakalarm.ui.screens.main;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.dima.mayakalarm.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private TextView tvAlarmStatus;
    private Button btnChangeLocale;
    private Button btnSetAlarm;
    private Button btnCancelAlarm;
    private Button btnOnTomorrow;
    private TimePicker tpTime;

    private MainContract.Presenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this, this);

        setupUI();

        btnChangeLocale.setOnClickListener(v -> mainPresenter.onSwitchLocaleClicked());

        btnSetAlarm.setOnClickListener(v -> mainPresenter.onScheduleAlarmClicked(tpTime.getHour(), tpTime.getMinute()));

        btnCancelAlarm.setOnClickListener(v -> mainPresenter.onSetAlarmOffClicked());

        btnOnTomorrow.setOnClickListener(v -> mainPresenter.onScheduleNextDayAlarmClicked());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.updateCurrentUI();
    }

    @Override
    public void showStateAlarmIsOn(long time) {
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
    }

    @Override
    public void showStateAlarmIsOff(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        tpTime.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        tpTime.setMinute(calendar.get(Calendar.MINUTE));
        tpTime.setVisibility(View.VISIBLE);

        btnSetAlarm.setVisibility(View.VISIBLE);
        btnCancelAlarm.setVisibility(View.GONE);
        btnOnTomorrow.setVisibility(View.GONE);
        tvAlarmStatus.setText(R.string.alarm_status_off);
    }

    @Override
    public void showLocaleSwitched(String localeText) {
        btnChangeLocale.setText(localeText);
    }

    @Override
    public void recreate() {
        super.recreate();
    }

    private void setupUI() {
        tvAlarmStatus = findViewById(R.id.tvAlarmSetStatus);
        btnChangeLocale = findViewById(R.id.btnChangeLocale);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        btnCancelAlarm = findViewById(R.id.btnCancelAlarm);
        btnOnTomorrow = findViewById(R.id.btnOnTomorrow);
        tpTime = findViewById(R.id.tpTime);
        tpTime.setIs24HourView(true);
    }
}