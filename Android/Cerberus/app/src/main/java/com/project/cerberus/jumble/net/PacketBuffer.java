package com.project.cerberus.jumble.net;

//import com.project.cerberus.mumbleclient.channel.ChannelListAdapter;
import com.project.cerberus.mumbleclient.service.PlumbleOverlay;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class PacketBuffer {
    private ByteBuffer mBuffer;

    public static PacketBuffer allocate(int len) {
        return new PacketBuffer(ByteBuffer.allocate(len));
    }

    public static PacketBuffer allocateDirect(int len) {
        return new PacketBuffer(ByteBuffer.allocateDirect(len));
    }

    public PacketBuffer(ByteBuffer buffer) {
        this.mBuffer = buffer;
    }

    public PacketBuffer(byte[] data, int len) {
        this.mBuffer = ByteBuffer.wrap(data);
        this.mBuffer.limit(len);
    }

    public int size() {
        return this.mBuffer.position();
    }

    public int capacity() {
        return this.mBuffer.limit();
    }

    public int left() {
        return this.mBuffer.limit() - this.mBuffer.position();
    }

    public void append(long v) {
        this.mBuffer.put((byte) ((int) v));
    }

    public void append(byte[] d, int len) {
        this.mBuffer.put(d, 0, len);
    }

    public void skip(int len) {
        this.mBuffer.position(this.mBuffer.position() + len);
    }

    public int next() {
        return this.mBuffer.get() & 255;
    }

    public ByteBuffer bufferBlock(int size) {
        if (size > this.mBuffer.remaining()) {
            throw new BufferUnderflowException();
        }
        ByteBuffer buffer = this.mBuffer.slice();
        buffer.limit(size);
        skip(size);
        return buffer;
    }

    public byte[] dataBlock(int size) {
        byte[] block = new byte[size];
        this.mBuffer.get(block, 0, size);
        return block;
    }

    public boolean readBool() {
        return ((int) readLong()) > 0;
    }

    public double readDouble() {
        if (left() >= 8) {
            return (double) ((long) (((((((next() | (next() << 8)) | (next() << 16)) | (next() << 24)) | (next() << 32)) | (next() << 40)) | (next() << 48)) | (next() << 56)));
        }
        throw new BufferUnderflowException();
    }

    public float readFloat() {
        if (left() >= 4) {
            return Float.intBitsToFloat(((next() | (next() << 8)) | (next() << 16)) | (next() << 24));
        }
        throw new BufferUnderflowException();
    }

    public long readLong() {
        long v = (long) next();
        if ((128 & v) == 0) {
            return v & 127;
        }
        if ((192 & v) == 128) {
            return ((63 & v) << 8) | ((long) next());
        }
        if ((240 & v) == 240) {
            switch ((int) (252 & v)) {
                case PlumbleOverlay.DEFAULT_HEIGHT /*240*/:
                    return (long) ((((next() << 24) | (next() << 16)) | (next() << 8)) | next());
                case 244:
                    return (long) ((((((((next() << 56) | (next() << 48)) | (next() << 40)) | (next() << 32)) | (next() << 24)) | (next() << 16)) | (next() << 8)) | next());
                case 248:
                    return readLong() ^ -1;
                case 252:
                    return (v & 3) ^ -1;
                default:
                    throw new BufferUnderflowException();
            }
        } else if ((240 & v) == 224) {
            return ((((15 & v) << 24) | ((long) (next() << 16))) | ((long) (next() << 8))) | ((long) next());
        } else {
            if ((224 & v) == 192) {
                return (((31 & v) << 16) | ((long) (next() << 8))) | ((long) next());
            }
            return 0;
        }
    }

    public void rewind() {
        this.mBuffer.rewind();
    }

    public void writeBool(boolean b) {
        writeLong((long) (b ? 1 : 0));
    }

    public void writeDouble(double v) {
        long i = Double.doubleToLongBits(v);
        append(i & 255);
        append((i >> 8) & 255);
        append((i >> 16) & 255);
        append((i >> 24) & 255);
        append((i >> 32) & 255);
        append((i >> 40) & 255);
        append((i >> 48) & 255);
        append((i >> 56) & 255);
    }

    public void writeFloat(float v) {
        int i = Float.floatToIntBits(v);
        append((long) (i & 255));
        append((long) ((i >> 8) & 255));
        append((long) ((i >> 16) & 255));
        append((long) ((i >> 24) & 255));
    }

    public void writeLong(long value) {
        long i = value;
        if ((Long.MIN_VALUE & i) > 0 && (-1 ^ i) < 4294967296L) {
            i ^= -1;
            if (i <= 3) {
                append(252 | i);
                return;
            }
            append(248);
        }
        if (i < 128) {
            append(i);
        } else if (i < 16384) {
            append((i >> 8) | 128);
            append(255 & i);
        } else if (i < 2097152) {
            append((i >> 16) | 192);
            append((i >> 8) & 255);
            append(255 & i);
        } else if (i < 268435456) {
            append((i >> 24) | 224);
            append((i >> 16) & 255);
            append((i >> 8) & 255);
            append(255 & i);
        } else if (i < 4294967296L) {
            append(240);
            append((i >> 24) & 255);
            append((i >> 16) & 255);
            append((i >> 8) & 255);
            append(255 & i);
        } else {
            append(244);
            append((i >> 56) & 255);
            append((i >> 48) & 255);
            append((i >> 40) & 255);
            append((i >> 32) & 255);
            append((i >> 24) & 255);
            append((i >> 16) & 255);
            append((i >> 8) & 255);
            append(255 & i);
        }
    }
}
