package com.dima.mayakalarm.ui.screens.ringing;

public interface AlarmRingingContract {

    interface View {

        void turnBacklightOn();

        void onStartDownload();

        void onGetRemoteInfo(int temp, int humidity, String description, int windSpeed);

        void onGetImageInfo(String picUrl);

        void onError();

        void onEndDownload();

        void finish();

    }

    interface Presenter {
        void onCreate();

        void getData();

        void playerOn();

        void playerOff();

        void backlightOn();

        void onScheduleNextDayClicked();

        void onSnoozeClicked();

    }
}
