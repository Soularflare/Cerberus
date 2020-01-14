package com.project.cerberus;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

// Created by batman on 3/4/18.


public class FcmMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN",s);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        // Logic to turn on the screen


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =  PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        @SuppressWarnings("deprecation") NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(remoteMessage.getData().get("Nick"));
        notificationBuilder.setContentText(remoteMessage.getData().get("body"));
        notificationBuilder.setSmallIcon(R.mipmap.eye_icon);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(alarmSound);
        long[] v = {500,1000};
        notificationBuilder.setVibrate(v);
        notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}