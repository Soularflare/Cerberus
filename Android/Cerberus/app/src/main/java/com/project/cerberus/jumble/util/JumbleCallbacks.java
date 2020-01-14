package com.project.cerberus.jumble.util;

import com.project.cerberus.jumble.model.IChannel;
import com.project.cerberus.jumble.model.IMessage;
import com.project.cerberus.jumble.model.IUser;

import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A composite wrapper around Jumble observers to easily broadcast to each observer.
 * Created by andrew on 12/07/14.
 */
public class JumbleCallbacks implements IJumbleObserver {
    private final Set<IJumbleObserver> mCallbacks;

    public JumbleCallbacks() {
        mCallbacks = Collections.newSetFromMap(new ConcurrentHashMap<IJumbleObserver, Boolean>());
    }

    public void registerObserver(IJumbleObserver observer) {
        mCallbacks.add(observer);
    }

    public void unregisterObserver(IJumbleObserver observer) {
        mCallbacks.remove(observer);
    }

    @Override
    public void onConnected() {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onConnected();
        }
    }

    @Override
    public void onConnecting() {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onConnecting();
        }
    }

    @Override
    public void onDisconnected(JumbleException e) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onDisconnected(e);
        }
    }

    @Override
    public void onTLSHandshakeFailed(X509Certificate[] chain) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onTLSHandshakeFailed(chain);
        }
    }

    @Override
    public void onChannelAdded(IChannel channel) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onChannelAdded(channel);
        }
    }

    @Override
    public void onChannelStateUpdated(IChannel channel) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onChannelStateUpdated(channel);
        }
    }

    @Override
    public void onChannelRemoved(IChannel channel) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onChannelRemoved(channel);
        }
    }

    @Override
    public void onChannelPermissionsUpdated(IChannel channel) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onChannelPermissionsUpdated(channel);
        }
    }

    @Override
    public void onUserConnected(IUser user) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onUserConnected(user);
        }
    }

    @Override
    public void onUserStateUpdated(IUser user) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onUserStateUpdated(user);
        }
    }

    @Override
    public void onUserTalkStateUpdated(IUser user) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onUserTalkStateUpdated(user);
        }
    }

    @Override
    public void onUserJoinedChannel(IUser user, IChannel newChannel, IChannel oldChannel) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onUserJoinedChannel(user, newChannel, oldChannel);
        }
    }

    @Override
    public void onUserRemoved(IUser user, String reason) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onUserRemoved(user, reason);
        }
    }

    @Override
    public void onPermissionDenied(String reason) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onPermissionDenied(reason);
        }
    }

    @Override
    public void onMessageLogged(IMessage message) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onMessageLogged(message);
        }
    }

    @Override
    public void onVoiceTargetChanged(VoiceTargetMode mode) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onVoiceTargetChanged(mode);
        }
    }

    @Override
    public void onLogInfo(String message) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onLogInfo(message);
        }
    }

    @Override
    public void onLogWarning(String message) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onLogWarning(message);
        }
    }

    @Override
    public void onLogError(String message) {
        for (IJumbleObserver observer : mCallbacks) {
            observer.onLogError(message);
        }
    }
}