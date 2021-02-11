package com.dima.mayakalarm.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DeviceRebootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmHelper alarmHelper = new AlarmHelper(context);
        alarmHelper.setAlarm(false);
    }
}

