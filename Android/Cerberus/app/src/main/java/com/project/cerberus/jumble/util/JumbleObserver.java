package com.project.cerberus.jumble.util;

import com.project.cerberus.jumble.model.IChannel;
import com.project.cerberus.jumble.model.IMessage;
import com.project.cerberus.jumble.model.IUser;

import java.security.cert.X509Certificate;

/**
 * Stub class for Jumble service observation.
 * Created by andrew on 31/07/13.
 */
public class JumbleObserver implements IJumbleObserver {
    @Override
    public void onConnected() {

    }

    @Override
    public void onConnecting() {

    }

    @Override
    public void onDisconnected(JumbleException e) {

    }

    @Override
    public void onTLSHandshakeFailed(X509Certificate[] chain) {

    }

    @Override
    public void onChannelAdded(IChannel channel) {

    }

    @Override
    public void onChannelStateUpdated(IChannel channel) {

    }

    @Override
    public void onChannelRemoved(IChannel channel) {

    }

    @Override
    public void onChannelPermissionsUpdated(IChannel channel) {

    }

    @Override
    public void onUserConnected(IUser user) {

    }

    @Override
    public void onUserStateUpdated(IUser user) {

    }

    @Override
    public void onUserTalkStateUpdated(IUser user) {

    }

    @Override
    public void onUserJoinedChannel(IUser user, IChannel newChannel, IChannel oldChannel) {

    }

    @Override
    public void onUserRemoved(IUser user, String reason) {

    }

    @Override
    public void onPermissionDenied(String reason) {

    }

    @Override
    public void onMessageLogged(IMessage message) {

    }

    @Override
    public void onVoiceTargetChanged(VoiceTargetMode mode) {

    }

    @Override
    public void onLogInfo(String message) {

    }

    @Override
    public void onLogWarning(String message) {

    }

    @Override
    public void onLogError(String message) {

    }
}