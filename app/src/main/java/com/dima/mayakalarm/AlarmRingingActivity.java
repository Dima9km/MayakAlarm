package com.dima.mayakalarm;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlarmRingingActivity extends AppCompatActivity {

    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "f52cbda5d0f59d7559eb126d5fc0e0e2";
    public static String lat = "51.6664";
    public static String lon = "39.17";
    public static String units = "metric";
    public static String lang = "ru";
    private TextView weatherText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringing);
        Button dismissButton = findViewById(R.id.btnDismiss);
        Button snoozeButton = findViewById(R.id.btnSnooze);
        weatherText = findViewById(R.id.tvWeather);

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

        Player player = new Player(getApplicationContext());
        player.play();
        getWeatherData();

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

                Alarm alarm = new Alarm(
                        AlarmRingingActivity.this,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                );

                alarm.setAlarm();
                player.stop();
                finish();
            }
        });
    }

    void getWeatherData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = weatherService.getCurrentWeatherData(lat, lon, AppId, units, lang);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    String currentWeather =
                            "Температура: " +
                                    weatherResponse.main.temp + " C" +
                                    "\n" +
                                    "Влажность: " +
                                    weatherResponse.main.humidity + " %" +
                                    "\n" +
                                    "Чо как: пока " +
                                    weatherResponse.weather.get(0).description +
                                    "\n" +
                                    "Ветерок: " +
                                    weatherResponse.wind.speed + " м/с";

                    weatherText.setText(currentWeather);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                weatherText.setText(t.getMessage());
            }
        });
    }

}
