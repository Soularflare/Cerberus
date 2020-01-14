package com.project.cerberus.mumbleclient.service.ipc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.project.cerberus.jumble.IJumbleService;
import com.project.cerberus.jumble.IJumbleSession;

/**
 * Created by andrew on 08/08/14.
 */
public class TalkBroadcastReceiver extends BroadcastReceiver {
    public static final String BROADCAST_TALK = "com.morlunk.mumbleclient.action.TALK";
    public static final String EXTRA_TALK_STATUS = "status";
    public static final String TALK_STATUS_ON = "on";
    public static final String TALK_STATUS_OFF = "off";
    public static final String TALK_STATUS_TOGGLE = "toggle";

    private IJumbleService mService;

    public TalkBroadcastReceiver(IJumbleService service) {
        mService = service;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BROADCAST_TALK.equals(intent.getAction())) {
            if (!mService.isConnected())
                return;
            IJumbleSession session = mService.getSession();
            String status = intent.getStringExtra(EXTRA_TALK_STATUS);
            if (status == null) status = TALK_STATUS_TOGGLE;
            if (TALK_STATUS_ON.equals(status)) {
                session.setTalkingState(true);
            } else if (TALK_STATUS_OFF.equals(status)) {
                session.setTalkingState(false);
            } else if (TALK_STATUS_TOGGLE.equals(status)) {
                session.setTalkingState(!session.isTalking());
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }
}