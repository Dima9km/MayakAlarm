package com.dima.mayakalarm;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button alarmOn;
    Button alarmOff;
    TextView alarmStatus;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmOn = findViewById(R.id.btnSetAlarm);
        alarmOff = findViewById(R.id.btnCancelAlarm);
        alarmStatus = findViewById(R.id.tvStatus);
        timePicker = findViewById(R.id.tpTime);
        timePicker.setIs24HourView(true);

        Calendar calendar = Calendar.getInstance();

        alarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                String time = DateFormat.format("HH.mm", calendar).toString();
                alarmStatus.setText("Будильник зазвучит в " + time);
                Alarm alarm = new Alarm(MainActivity.this, timePicker.getHour(), timePicker.getMinute());
                alarm.setAlarm();

            }
        });

        alarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmStatus.setText("Будильник выключен");

            }
        });
    }


}