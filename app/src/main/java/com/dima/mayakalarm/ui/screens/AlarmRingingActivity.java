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
import com.dima.mayakalarm.model.InfoToShow;
import com.dima.mayakalarm.repository.Repository;
import com.dima.mayakalarm.repository.RepositoryListener;
import com.dima.mayakalarm.util.AlarmHelper;
import com.dima.mayakalarm.util.Player;
import com.squareup.picasso.Picasso;

public class AlarmRingingActivity extends AppCompatActivity {

    private TextView weatherText;
    private ImageView imageDaily;
    private ProgressBar preloader;
    private AlarmHelper alarmHelper;

    private Player player;

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
            weatherText.setText("У природы нет плохой погоды");
            imageDaily.setImageResource(R.drawable.galaxy);
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

        alarmHelper = new AlarmHelper(getApplicationContext());
        player = new Player(this);

        Button dismissButton = findViewById(R.id.btnDismiss);
        Button snoozeButton = findViewById(R.id.btnSnooze);
        weatherText = findViewById(R.id.tvWeather);
        preloader = findViewById(R.id.pbPreloader);
        imageDaily = findViewById(R.id.ivPicture);

        turnBacklightOn();
        player.play();
        repository.getInfoToShow();

        dismissButton.setOnClickListener(v -> {
            alarmHelper.scheduleNextDayAlarm();
            player.stop();
            finish();
        });

        snoozeButton.setOnClickListener(v -> {
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
