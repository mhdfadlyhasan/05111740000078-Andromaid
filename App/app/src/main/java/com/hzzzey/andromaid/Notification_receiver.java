package com.hzzzey.andromaid;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class Notification_receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ScheduleNotification mNotification = new ScheduleNotification(context);
        NotificationManager notificationManager = mNotification.getManager();
        Intent activity = new Intent(context,MainActivity.class);
        activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,activity,PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("test","" + "call notification!");
        String Message = "Doc You got class NOW!";
        Toast.makeText(context, "Alarm running", Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder nbuilder = mNotification.getChannel1Notification("Andromaid",Message).setContentIntent(pendingIntent);

        notificationManager.notify(1, nbuilder.build());
    }
}
