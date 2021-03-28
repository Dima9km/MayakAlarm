package com.dima.mayakalarm.ui.screens.main;

import android.content.Context;

import com.dima.mayakalarm.util.AlarmHelper;
import com.dima.mayakalarm.util.LocaleManager;
import com.dima.mayakalarm.util.NotificationHelper;

public class MainPresenter implements MainContract.Presenter {

    public final NotificationHelper notificationHelper;
    public final AlarmHelper alarmHelper;
    public final LocaleManager localeManager;

    private final MainContract.View mainView;

    public MainPresenter(MainContract.View mainView, Context context) {
        this.mainView = mainView;
        notificationHelper = new NotificationHelper(context);
        alarmHelper = new AlarmHelper(context);
        localeManager = new LocaleManager(context);
    }

    @Override
    public void onSwitchLocaleClicked() {
        localeManager.switchLocale();
        mainView.showLocaleSwitched(localeManager.getCurrentLocale());
        mainView.recreate();
    }

    @Override
    public void onScheduleAlarmClicked(int hour, int minute) {
        alarmHelper.scheduleAlarm(hour, minute);
        mainView.showStateAlarmIsOn(alarmHelper.getAlarm().getTime());
        notificationHelper.show();
    }

    @Override
    public void onSetAlarmOffClicked() {
        alarmHelper.setAlarmOff();
        mainView.showStateAlarmIsOff(alarmHelper.getAlarm().getTime());
        notificationHelper.hide();
    }

    @Override
    public void onScheduleNextDayAlarmClicked() {
        alarmHelper.scheduleNextDayAlarm();
        mainView.showStateAlarmIsOn(alarmHelper.getAlarm().getTime());
        notificationHelper.show();
    }

    @Override
    public void updateCurrentUI() {
        if (!alarmHelper.getAlarm().isAlarmOn()) {
            mainView.showStateAlarmIsOff(alarmHelper.getAlarm().getTime());
            notificationHelper.hide();
        } else {
            mainView.showStateAlarmIsOn(alarmHelper.getAlarm().getTime());
            notificationHelper.show();
        }

    }
}
