package com.project.cerberus.mumbleclient.channel.actionmode;

import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.Menu;
import com.project.cerberus.mumbleclient.channel.ChatTargetProvider;
import com.project.cerberus.mumbleclient.channel.ChatTargetProvider.ChatTarget;

public abstract class ChatTargetActionModeCallback implements Callback {
    private ChatTargetProvider mProvider;

    public abstract ChatTarget getChatTarget();

    public ChatTargetActionModeCallback(ChatTargetProvider provider) {
        this.mProvider = provider;
    }

    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.mProvider.setChatTarget(getChatTarget());
        return true;
    }

    public void onDestroyActionMode(ActionMode actionMode) {
        this.mProvider.setChatTarget(null);
    }
}
