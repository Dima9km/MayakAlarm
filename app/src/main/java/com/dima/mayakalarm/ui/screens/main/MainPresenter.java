package com.dima.mayakalarm.ui.screens.main;

import com.dima.mayakalarm.util.AlarmHelper;
import com.dima.mayakalarm.util.LocaleManager;
import com.dima.mayakalarm.util.NotificationHelper;

public class MainPresenter implements MainContract.Presenter {

    private final NotificationHelper notificationHelper;
    private final AlarmHelper alarmHelper;
    private final LocaleManager localeManager;

    private final MainContract.View mainView;

    public MainPresenter(MainContract.View mainView, NotificationHelper notificationHelper,
                         AlarmHelper alarmHelper, LocaleManager localeManager) {
        this.mainView = mainView;
        this.notificationHelper = notificationHelper;
        this.alarmHelper = alarmHelper;
        this.localeManager = localeManager;
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
    public void onResume() {
        if (!alarmHelper.getAlarm().isAlarmOn()) {
            mainView.showStateAlarmIsOff(alarmHelper.getAlarm().getTime());
            notificationHelper.hide();
        } else {
            mainView.showStateAlarmIsOn(alarmHelper.getAlarm().getTime());
            notificationHelper.show();
        }
    }
}
