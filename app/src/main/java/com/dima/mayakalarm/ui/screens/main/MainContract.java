package com.dima.mayakalarm.ui.screens.main;

public interface MainContract {

    interface View {
        void showStateAlarmIsOn(long time);

        void showStateAlarmIsOff(long time);

        void showLocaleSwitched(String localeText);

        void recreate();
    }

    interface Presenter {
        void onSwitchLocaleClicked();

        void onScheduleAlarmClicked(int hour, int minute);

        void onSetAlarmOffClicked();

        void onScheduleNextDayAlarmClicked();

        void onResume();
    }
}
