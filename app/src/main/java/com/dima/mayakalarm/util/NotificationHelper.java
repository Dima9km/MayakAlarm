package com.dima.mayakalarm.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.dima.mayakalarm.R;

public class NotificationHelper {

    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";

    public Notification getNotification(Context context) {
        return new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                .setContentText("Будильник включён")
                .setSmallIcon(R.drawable.ic_alarm_notification)
                .build();
    }

    public NotificationManager getManager(Context context) {
        return context.getSystemService(NotificationManager.class);
    }
}
