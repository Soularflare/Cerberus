package com.project.cerberus.jumble.protocol;

import com.project.cerberus.jumble.net.JumbleUDPMessageType;

public interface JumbleUDPMessageListener {

    public static class Stub implements JumbleUDPMessageListener {
        public void messageUDPPing(byte[] data) {
        }

        public void messageVoiceData(byte[] data, JumbleUDPMessageType messageType) {
        }
    }

    void messageUDPPing(byte[] bArr);

    void messageVoiceData(byte[] bArr, JumbleUDPMessageType jumbleUDPMessageType);
}
