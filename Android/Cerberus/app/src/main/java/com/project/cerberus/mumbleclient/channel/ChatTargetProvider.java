package com.project.cerberus.mumbleclient.channel;

import com.project.cerberus.jumble.model.Channel;
import com.project.cerberus.jumble.model.User;

public interface ChatTargetProvider {

    public static class ChatTarget {
        private Channel mChannel;
        private User mUser;

        public ChatTarget(Channel channel) {
            this.mChannel = channel;
        }

        public ChatTarget(User user) {
            this.mUser = user;
        }

        public Channel getChannel() {
            return this.mChannel;
        }

        public User getUser() {
            return this.mUser;
        }
    }

    public interface OnChatTargetSelectedListener {
        void onChatTargetSelected(ChatTarget chatTarget);
    }

    ChatTarget getChatTarget();

    void registerChatTargetListener(OnChatTargetSelectedListener onChatTargetSelectedListener);

    void setChatTarget(ChatTarget chatTarget);

    void unregisterChatTargetListener(OnChatTargetSelectedListener onChatTargetSelectedListener);
}
