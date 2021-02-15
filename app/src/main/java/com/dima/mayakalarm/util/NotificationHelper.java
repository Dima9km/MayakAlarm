package com.dima.mayakalarm.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.dima.mayakalarm.R;

public class NotificationHelper {

    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";
    private final Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public Notification getNotification() {
        return new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                .setContentText("Будильник включён")
                .setSmallIcon(R.drawable.ic_alarm_notification)
                .setOngoing(true)
                .build();
    }

    public NotificationManager getManager() {
        return context.getSystemService(NotificationManager.class);
    }

    public void show(){
        getManager().notify(1, getNotification());
    }

    public void hide(){
        getManager().cancel(1);
    }
}
