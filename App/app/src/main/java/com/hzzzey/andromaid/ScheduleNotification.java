package com.hzzzey.andromaid;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;


public class ScheduleNotification extends ContextWrapper {
    public static final String Channel1ID = "channel1ID";
    private NotificationManager mManager;

    public ScheduleNotification(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel channel1 = new NotificationChannel(Channel1ID, "Name", NotificationManager.IMPORTANCE_DEFAULT);

        channel1.setDescription("This is notification!");
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String Message)
    {
        return new NotificationCompat.Builder(getApplicationContext(),Channel1ID)
                .setContentTitle(title).setContentText(Message).setSmallIcon(R.drawable.ic_home_black_24dp);
    }

}