package com.project.cerberus.mumbleclient.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.InboxStyle;

import com.project.cerberus.R;
//import com.project.cerberus.mumbleclient.app.PlumbleActivity;

import java.util.ArrayList;
import java.util.List;

public class PlumbleNotification {
    private static final String BROADCAST_CANCEL_RECONNECT = "b_cancel_reconnect";
    private static final String BROADCAST_DEAFEN = "b_deafen";
    private static final String BROADCAST_MUTE = "b_mute";
    private static final String BROADCAST_OVERLAY = "b_overlay";
    private static final int NOTIFICATION_ID = 1;
    private String mCustomContentText;
    private String mCustomTicker;
    private OnActionListener mListener;
    private List<String> mMessages;
    private BroadcastReceiver mNotificationReceiver = new C02781();
    private boolean mReconnecting;
    private Service mService;

    /* renamed from: com.project.cerberus.mumbleclient.service.PlumbleNotification$1 */
    class C02781 extends BroadcastReceiver {
        C02781() {
        }

        public void onReceive(Context context, Intent intent) {
            if (PlumbleNotification.BROADCAST_MUTE.equals(intent.getAction())) {
                PlumbleNotification.this.mListener.onMuteToggled();
            } else if (PlumbleNotification.BROADCAST_DEAFEN.equals(intent.getAction())) {
                PlumbleNotification.this.mListener.onDeafenToggled();
            } else if (PlumbleNotification.BROADCAST_OVERLAY.equals(intent.getAction())) {
                PlumbleNotification.this.mListener.onOverlayToggled();
            } else if (PlumbleNotification.BROADCAST_CANCEL_RECONNECT.equals(intent.getAction())) {
                PlumbleNotification.this.mListener.onReconnectCanceled();
            }
        }
    }

    public interface OnActionListener {
        void onDeafenToggled();

        void onMuteToggled();

        void onOverlayToggled();

        void onReconnectCanceled();
    }

    public static PlumbleNotification showForeground(Service service, OnActionListener listener) {
        PlumbleNotification notification = new PlumbleNotification(service, listener);
        notification.show();
        return notification;
    }

    private PlumbleNotification(Service service, OnActionListener listener) {
        this.mService = service;
        this.mListener = listener;
        this.mMessages = new ArrayList();
        this.mCustomTicker = this.mService.getString(R.string.plumbleConnected);
        this.mCustomContentText = this.mService.getString(R.string.connected);
        this.mReconnecting = false;
    }

    public void setCustomTicker(String ticker) {
        this.mCustomTicker = ticker;
        createNotification();
    }

    public void setCustomContentText(String text) {
        this.mCustomContentText = text;
        createNotification();
    }

    public void setReconnecting(boolean reconnecting) {
        this.mReconnecting = reconnecting;
        createNotification();
    }

    public void addMessage(String message) {
        this.mMessages.add(message);
        this.mCustomTicker = message;
        createNotification();
    }

    public void clearMessages() {
        this.mMessages.clear();
        createNotification();
    }

    public void show() {
        createNotification();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_DEAFEN);
        filter.addAction(BROADCAST_MUTE);
        filter.addAction(BROADCAST_OVERLAY);
        filter.addAction(BROADCAST_CANCEL_RECONNECT);
        try {
            this.mService.registerReceiver(this.mNotificationReceiver, filter);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void hide() {
        try {
            this.mService.unregisterReceiver(this.mNotificationReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        this.mService.stopForeground(true);
    }

    private Notification createNotification() {
        Builder builder = new Builder(this.mService);
        builder.setSmallIcon(R.drawable.ic_stat_notify);
        builder.setTicker(this.mCustomTicker);
        builder.setContentTitle(this.mService.getString(R.string.app_name));
        builder.setContentText(this.mCustomContentText);
        builder.setPriority(1);
        builder.setOngoing(true);
        if (this.mReconnecting) {
            builder.addAction(R.drawable.ic_action_delete_dark, this.mService.getString(R.string.cancel_reconnect), PendingIntent.getBroadcast(this.mService, 2, new Intent(BROADCAST_CANCEL_RECONNECT), 268435456));
        } else {
            Intent muteIntent = new Intent(BROADCAST_MUTE);
            Intent deafenIntent = new Intent(BROADCAST_DEAFEN);
            Intent overlayIntent = new Intent(BROADCAST_OVERLAY);
            builder.addAction(R.drawable.ic_action_microphone, this.mService.getString(R.string.mute), PendingIntent.getBroadcast(this.mService, 1, muteIntent, 268435456));
            builder.addAction(R.drawable.ic_action_audio, this.mService.getString(R.string.deafen), PendingIntent.getBroadcast(this.mService, 1, deafenIntent, 268435456));
            builder.addAction(R.drawable.ic_action_channels, this.mService.getString(R.string.overlay), PendingIntent.getBroadcast(this.mService, 2, overlayIntent, 268435456));
        }
        if (this.mMessages.size() > 0) {
            InboxStyle inboxStyle = new InboxStyle();
            for (String message : this.mMessages) {
                inboxStyle.addLine(message);
            }
            builder.setStyle(inboxStyle);
        }
//        Intent channelListIntent = new Intent(this.mService, PlumbleActivity.class);
//        channelListIntent.putExtra(PlumbleActivity.EXTRA_DRAWER_FRAGMENT, 1);
//        builder.setContentIntent(PendingIntent.getActivity(this.mService, 0, channelListIntent, 268435456));
        Notification notification = builder.build();
        this.mService.startForeground(1, notification);
        return notification;
    }
}
