package com.project.cerberus.jumble.audio;

import com.project.cerberus.jumble.audio.javacpp.Speex.SpeexResampler;
import com.project.cerberus.jumble.exception.NativeAudioException;

public class ResamplingEncoder implements IEncoder {
    private static final int SPEEX_RESAMPLE_QUALITY = 3;
    private IEncoder mEncoder;
    private SpeexResampler mResampler;

    public ResamplingEncoder(IEncoder encoder, int channels, int inputSampleRate, int targetSampleRate) {
        this.mEncoder = encoder;
        this.mResampler = new SpeexResampler(channels, inputSampleRate, targetSampleRate, 3);
    }

    public int encode(short[] input, int inputSize, byte[] output, int outputSize) throws NativeAudioException {
        this.mResampler.resample(input, input);
        return this.mEncoder.encode(input, inputSize, output, outputSize);
    }

    public void setBitrate(int bitrate) {
        this.mEncoder.setBitrate(bitrate);
    }

    public void setEncoder(IEncoder encoder) {
        if (this.mEncoder != null) {
            this.mEncoder.destroy();
        }
        this.mEncoder = encoder;
    }

    public void destroy() {
        this.mResampler.destroy();
        this.mEncoder.destroy();
        this.mResampler = null;
        this.mEncoder = null;
    }
}
