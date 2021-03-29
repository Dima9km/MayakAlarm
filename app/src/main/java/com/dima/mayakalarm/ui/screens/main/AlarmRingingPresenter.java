package com.dima.mayakalarm.ui.screens.main;

import com.dima.mayakalarm.repository.Repository;
import com.dima.mayakalarm.repository.RepositoryListener;
import com.dima.mayakalarm.util.AlarmHelper;
import com.dima.mayakalarm.util.Player;

public class AlarmRingingPresenter implements AlarmRingingContract.Presenter {

    private final AlarmRingingContract.View alarmRingingView;
    private final Player player;
    private final AlarmHelper alarmHelper;
    private final Repository repository;

    public AlarmRingingPresenter(AlarmRingingContract.View alarmRingingView,
                                 Player player, AlarmHelper alarmHelper, Repository repository) {
        this.alarmRingingView = alarmRingingView;
        this.player = player;
        this.alarmHelper = alarmHelper;
        this.repository = repository;
    }

    @Override
    public void onCreate() {
        backlightOn();
        playerOn();
        getData();
    }

    @Override
    public void getData() {
        repository.getInfo(new RepositoryListener() {

            @Override
            public void onStartDownload() {
                alarmRingingView.onStartDownload();
            }

            @Override
            public void onGetRemoteInfo(int temp, int humidity, String description, int windSpeed) {
                alarmRingingView.onGetRemoteInfo(temp, humidity, description, windSpeed);
            }

            @Override
            public void onGetImageInfo(String picUrl) {
                alarmRingingView.onGetImageInfo(picUrl);
            }

            @Override
            public void onError() {
                alarmRingingView.onError();
            }

            @Override
            public void onEndDownload() {
                alarmRingingView.onEndDownload();
            }
        });
    }

    @Override
    public void backlightOn() {
        alarmRingingView.turnBacklightOn();
    }

    @Override
    public void playerOn() {
        player.play();
    }

    @Override
    public void playerOff() {
        player.stop();
    }

    @Override
    public void onScheduleNextDayClicked() {
        alarmHelper.scheduleNextDayAlarm();
        playerOff();
        alarmRingingView.finish();
    }

    @Override
    public void onSnoozeClicked() {
        alarmHelper.scheduleSnoozedAlarm();
        playerOff();
        alarmRingingView.finish();
    }
}
