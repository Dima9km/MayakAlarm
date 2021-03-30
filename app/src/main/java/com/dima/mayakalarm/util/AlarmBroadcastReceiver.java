package com.dima.mayakalarm.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dima.mayakalarm.ui.screens.ringing.AlarmRingingActivity;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startActivity(new Intent(context, AlarmRingingActivity.class));
    }
}
