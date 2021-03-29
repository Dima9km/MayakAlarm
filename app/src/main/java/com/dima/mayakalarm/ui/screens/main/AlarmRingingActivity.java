package com.dima.mayakalarm.ui.screens.main;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dima.mayakalarm.R;
import com.dima.mayakalarm.repository.Repository;
import com.dima.mayakalarm.util.AlarmHelper;
import com.dima.mayakalarm.util.Player;
import com.squareup.picasso.Picasso;

public class AlarmRingingActivity extends AppCompatActivity implements AlarmRingingContract.View {

    private TextView tvWeather;
    private ImageView ivPicture;
    private ProgressBar pbLoader;

    private AlarmRingingContract.Presenter alarmRingingPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringing);

        Button btnNextDay = findViewById(R.id.btnNextDay);
        Button btnSnooze = findViewById(R.id.btnSnooze);

        tvWeather = findViewById(R.id.tvWeather);
        pbLoader = findViewById(R.id.pbPreloader);
        ivPicture = findViewById(R.id.ivPicture);

        alarmRingingPresenter = new AlarmRingingPresenter(this,
                new Player(this),
                new AlarmHelper(getApplicationContext()),
                Repository.getInstance(this));

        alarmRingingPresenter.onCreate();

        btnNextDay.setOnClickListener(v -> alarmRingingPresenter.onScheduleNextDayClicked());

        btnSnooze.setOnClickListener(v -> alarmRingingPresenter.onSnoozeClicked());
    }

    @Override
    public void onStartDownload() {
        pbLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetRemoteInfo(int temp, int humidity, String description, int windSpeed) {
        tvWeather.setText(String.format(getResources()
                .getString(R.string.weather), temp, humidity, description, windSpeed));
    }

    @Override
    public void onGetImageInfo(String picUrl) {
        Picasso.with(getApplicationContext())
                .load(picUrl)
                .into(ivPicture);
    }

    @Override
    public void onError() {
        tvWeather.setText(R.string.empty_state_text);
        ivPicture.setImageResource(R.drawable.galaxy);
    }

    @Override
    public void onEndDownload() {
        pbLoader.setVisibility(View.GONE);
    }

    @Override
    public void turnBacklightOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            this.setTurnScreenOn(true);
        } else {
            final Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
