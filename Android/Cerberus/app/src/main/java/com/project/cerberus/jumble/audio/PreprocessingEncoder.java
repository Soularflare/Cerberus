package com.project.cerberus.jumble.audio;

import com.googlecode.javacpp.IntPointer;
import com.project.cerberus.jumble.audio.javacpp.Speex.SpeexPreprocessState;
import com.project.cerberus.jumble.exception.NativeAudioException;

public class PreprocessingEncoder implements IEncoder {
    private IEncoder mEncoder;
    private SpeexPreprocessState mPreprocessor;

    public PreprocessingEncoder(IEncoder encoder, int frameSize, int sampleRate) {
        this.mEncoder = encoder;
        this.mPreprocessor = new SpeexPreprocessState(frameSize, sampleRate);
        IntPointer arg = new IntPointer(1);
        arg.put(0);
        this.mPreprocessor.control(4, arg);
        arg.put(1);
        this.mPreprocessor.control(2, arg);
        this.mPreprocessor.control(0, arg);
        this.mPreprocessor.control(8, arg);
        arg.put(30000);
        this.mPreprocessor.control(46, arg);
    }

    public int encode(short[] input, int inputSize, byte[] output, int outputSize) throws NativeAudioException {
        this.mPreprocessor.preprocess(input);
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
        this.mPreprocessor.destroy();
        this.mEncoder.destroy();
        this.mPreprocessor = null;
        this.mEncoder = null;
    }
}
