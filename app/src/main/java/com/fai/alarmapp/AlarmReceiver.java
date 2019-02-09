package com.fai.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Fai on 09/02/2019.
 */

public class AlarmReceiver extends BroadcastReceiver{
    public static final String TYPE_ONE_TIME = "oneTimeAlarm";
    public static final String TYPE_REPEATING = "repeatingAlarm";
    public static final String EXTRA_NOTE = "note";
    public static final String EXTRA_TYPE = "type";

    private final int NOTIF_ID_ONE_TIME = 100;
    private final int NOTIF_ID_REPEATING = 101;

    public AlarmReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        
    }


}
