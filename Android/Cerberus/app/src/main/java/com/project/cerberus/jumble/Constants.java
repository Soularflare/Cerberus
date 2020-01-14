package com.project.cerberus.jumble;

import com.googlecode.javacpp.IntPointer;
import com.googlecode.javacpp.Pointer;
import com.project.cerberus.jumble.audio.javacpp.CELT11;
import com.project.cerberus.jumble.audio.javacpp.CELT7;
import com.project.cerberus.jumble.protocol.AudioHandler;

public class Constants {
    public static int CELT_11_VERSION = 0;
    public static int CELT_7_VERSION = 0;
    public static final int DEFAULT_PORT = 64738;
    public static final int PROTOCOL_MAJOR = 1;
    public static final int PROTOCOL_MINOR = 2;
    public static final int PROTOCOL_PATCH = 5;
    public static final String PROTOCOL_STRING = "1.2.5";
    public static final int PROTOCOL_VERSION = 66053;
    public static final String TAG = "Jumble";
    public static final int TRANSMIT_CONTINUOUS = 2;
    public static final int TRANSMIT_PUSH_TO_TALK = 1;
    public static final int TRANSMIT_VOICE_ACTIVITY = 0;

    static {
        Pointer celt11Mode = CELT11.celt_mode_create(AudioHandler.SAMPLE_RATE, 480, null);
        Pointer celt7Mode = CELT7.celt_mode_create(AudioHandler.SAMPLE_RATE, 480, null);
        IntPointer celt11Version = new IntPointer(new int[0]);
        IntPointer celt7Version = new IntPointer(new int[0]);
        CELT11.celt_mode_info(celt11Mode, 2000, celt11Version);
        CELT7.celt_mode_info(celt7Mode, 2000, celt7Version);
        CELT11.celt_mode_destroy(celt11Mode);
        CELT7.celt_mode_destroy(celt7Mode);
        CELT_11_VERSION = celt11Version.get();
        CELT_7_VERSION = celt7Version.get();
    }
}
