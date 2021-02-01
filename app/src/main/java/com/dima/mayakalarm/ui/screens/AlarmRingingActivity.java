package com.dima.mayakalarm.ui.screens;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dima.mayakalarm.R;
import com.dima.mayakalarm.model.Alarm;
import com.dima.mayakalarm.model.InfoToShow;
import com.dima.mayakalarm.repository.Repository;
import com.dima.mayakalarm.repository.RepositoryListener;
import com.dima.mayakalarm.util.Player;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class AlarmRingingActivity extends AppCompatActivity {

    private TextView weatherText;
    private ImageView imageDaily;
    private ProgressBar preloader;


    private final Repository repository = new Repository(new RepositoryListener() {

        @Override
        public void onStartDownload() {
            preloader.setVisibility(View.VISIBLE);
        }

        @Override
        public void onGetRemoteInfo(InfoToShow infoToShow) {
            weatherText.setText(infoToShow.getCurrentWeather());
            Picasso.with(getApplicationContext())
                    .load(infoToShow.getImageUrl())
                    .into(imageDaily);
        }

        @Override
        public void onError(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onEndDownload() {
            preloader.setVisibility(View.GONE);
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringing);

        Button dismissButton = findViewById(R.id.btnDismiss);
        Button snoozeButton = findViewById(R.id.btnSnooze);
        weatherText = findViewById(R.id.tvWeather);
        preloader = findViewById(R.id.pbPreloader);
        imageDaily = findViewById(R.id.ivPicture);

        turnBacklightOn();

        Player player = new Player(getApplicationContext());
        player.play();

        repository.getInfoToShow();

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                finish();
            }
        });

        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 10);

                Alarm snoozedAlarm = new Alarm(calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                );

                snoozedAlarm.setAlarm(getApplicationContext());
                player.stop();
                finish();
            }
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
