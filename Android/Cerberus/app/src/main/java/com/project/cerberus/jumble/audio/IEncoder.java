package com.project.cerberus.jumble.audio;

import com.project.cerberus.jumble.exception.NativeAudioException;

public interface IEncoder {
    void destroy();

    int encode(short[] sArr, int i, byte[] bArr, int i2) throws NativeAudioException;

    void setBitrate(int i);
}
