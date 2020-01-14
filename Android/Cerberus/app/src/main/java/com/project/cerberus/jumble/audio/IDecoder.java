package com.project.cerberus.jumble.audio;

import com.project.cerberus.jumble.exception.NativeAudioException;
import java.nio.ByteBuffer;

public interface IDecoder {
    int decodeFloat(ByteBuffer byteBuffer, int i, float[] fArr, int i2) throws NativeAudioException;

    int decodeShort(ByteBuffer byteBuffer, int i, short[] sArr, int i2) throws NativeAudioException;

    void destroy();
}
