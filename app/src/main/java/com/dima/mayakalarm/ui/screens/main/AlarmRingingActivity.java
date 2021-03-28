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
import com.dima.mayakalarm.repository.RepositoryListener;
import com.dima.mayakalarm.util.AlarmHelper;
import com.dima.mayakalarm.util.Player;
import com.squareup.picasso.Picasso;

public class AlarmRingingActivity extends AppCompatActivity {

    private TextView tvWeather;
    private ImageView ivPicture;
    private ProgressBar pbLoader;
    private AlarmHelper alarmHelper;

    private Player player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringing);

        alarmHelper = new AlarmHelper(getApplicationContext());
        player = new Player(this);

        Button btnDismiss = findViewById(R.id.btnDismiss);
        Button btnSnooze = findViewById(R.id.btnSnooze);
        tvWeather = findViewById(R.id.tvWeather);
        pbLoader = findViewById(R.id.pbPreloader);
        ivPicture = findViewById(R.id.ivPicture);

        Repository repository = Repository.getInstance(this);
        RepositoryListener repositoryListener = new RepositoryListener() {

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
            public void onError(String message) {
                tvWeather.setText(R.string.empty_state_text);
                ivPicture.setImageResource(R.drawable.galaxy);
            }

            @Override
            public void onEndDownload() {
                pbLoader.setVisibility(View.GONE);
            }
        };

        turnBacklightOn();
        player.play();
        repository.getInfo(repositoryListener);

        btnDismiss.setOnClickListener(v -> {
            alarmHelper.scheduleNextDayAlarm();
            player.stop();
            finish();
        });

        btnSnooze.setOnClickListener(v -> {
            alarmHelper.scheduleSnoozedAlarm();
            player.stop();
            finish();
        });
    }

    private void turnBacklightOn() {
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
}
