package com.fai.alarmapp;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.NestedScrollingChild;
import android.widget.Toast;

import java.util.Calendar;

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
        String type = intent.getStringExtra(EXTRA_TYPE);
        String note = intent.getStringExtra(EXTRA_NOTE);
        String title = type.equalsIgnoreCase(TYPE_ONE_TIME) ? "One TIme Alarm" : "Repeating Alarm";
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONE_TIME : NOTIF_ID_REPEATING;
        showAlarmNotification(context, title, note, notifId);
    }

    private void showAlarmNotification(Context context, String title, String note, int notifId){
        NotificationManager notificationManagerCompat = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                .setContentTitle(title)
                .setContentText(note)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        notificationManagerCompat.notify(notifId, builder.build());
    }

    public void setOneTimeAlarm(Context context, String type, String date, String time, String note){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_NOTE, note);
        intent.putExtra(EXTRA_TYPE, type);
        String dateArray[] = date.split("-");
        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1])-1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        int requestCode = NOTIF_ID_ONE_TIME;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(context, "One time alarm setup", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context, String type){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONE_TIME : NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

}
