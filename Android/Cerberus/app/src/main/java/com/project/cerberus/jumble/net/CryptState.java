package com.project.cerberus.jumble.net;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.tls.CipherSuite;

public class CryptState {
    public static final int AES_BLOCK_SIZE = 16;
    private static final String AES_TRANSFORMATION = "AES/ECB/NoPadding";
    Cipher mDecryptCipher;
    byte[] mDecryptHistory = new byte[256];
    byte[] mDecryptIV = new byte[16];
    Cipher mEncryptCipher;
    byte[] mEncryptIV = new byte[16];
    boolean mInit = false;
    long mLastGoodStart;
    long mLastRequestStart;
    byte[] mRawKey = new byte[16];
    int mUiGood = 0;
    int mUiLate = 0;
    int mUiLost = 0;
    int mUiRemoteGood = 0;
    int mUiRemoteLate = 0;
    int mUiRemoteLost = 0;
    int mUiRemoteResync = 0;
    int mUiResync = 0;

    private static class CryptSupport {
        private static final int SHIFTBITS = 7;

        private CryptSupport() {
        }

        public static void XOR(byte[] dst, byte[] a, byte[] b) {
            for (int i = 0; i < 16; i++) {
                dst[i] = (byte) (a[i] ^ b[i]);
            }
        }

        public static void S2(byte[] block) {
            int carry = (block[0] >> 7) & 1;
            for (int i = 0; i < 15; i++) {
                block[i] = (byte) ((block[i] << 1) | ((block[i + 1] >> 7) & 1));
            }
            block[15] = (byte) ((block[15] << 1) ^ (carry * CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA));
        }

        public static void S3(byte[] block) {
            int carry = (block[0] >> 7) & 1;
            for (int i = 0; i < 15; i++) {
                block[i] = (byte) (block[i] ^ ((block[i] << 1) | ((block[i + 1] >> 7) & 1)));
            }
            block[15] = (byte) (block[15] ^ ((block[15] << 1) ^ (carry * CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA)));
        }

        public static void ZERO(byte[] block) {
            Arrays.fill(block, (byte) 0);
        }
    }

    public boolean isValid() {
        return this.mInit;
    }

    public long getLastGoodElapsed() {
        return (System.nanoTime() - this.mLastGoodStart) / 1000;
    }

    public long getLastRequestElapsed() {
        return (System.nanoTime() - this.mLastRequestStart) / 1000;
    }

    public void resetLastRequestTime() {
        this.mLastRequestStart = System.nanoTime();
    }

    public byte[] getEncryptIV() {
        return this.mEncryptIV;
    }

    public byte[] getDecryptIV() {
        return this.mDecryptIV;
    }

    public synchronized void setKeys(byte[] rkey, byte[] eiv, byte[] div) throws InvalidKeyException {
        try {
            this.mEncryptCipher = Cipher.getInstance(AES_TRANSFORMATION);
            this.mDecryptCipher = Cipher.getInstance(AES_TRANSFORMATION);
            SecretKeySpec cryptKey = new SecretKeySpec(rkey, "AES");
            this.mRawKey = new byte[rkey.length];
            System.arraycopy(rkey, 0, this.mRawKey, 0, 16);
            this.mEncryptIV = new byte[eiv.length];
            System.arraycopy(eiv, 0, this.mEncryptIV, 0, 16);
            this.mDecryptIV = new byte[div.length];
            System.arraycopy(div, 0, this.mDecryptIV, 0, 16);
            this.mEncryptCipher.init(1, cryptKey);
            this.mDecryptCipher.init(2, cryptKey);
            this.mInit = true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        }
    }

    public synchronized byte[] decrypt(byte[] source, int length) throws BadPaddingException, IllegalBlockSizeException, ShortBufferException {
        byte[] bArr;
        if (length < 4) {
            bArr = null;
        } else {
            int plainLength = length - 4;
            bArr = new byte[plainLength];
            byte[] saveiv = new byte[16];
            short ivbyte = (short) (source[0] & 255);
            boolean restore = false;
            byte[] tag = new byte[16];
            int lost = 0;
            int late = 0;
            System.arraycopy(this.mDecryptIV, 0, saveiv, 0, 16);
            int i;
            byte[] bArr2;
            byte b;
            if (((this.mDecryptIV[0] + 1) & 255) != ivbyte) {
                int diff = ivbyte - (this.mDecryptIV[0] & 255);
                if (diff > 128) {
                    diff -= 256;
                } else if (diff < -128) {
                    diff += 256;
                }
                if (ivbyte < (this.mDecryptIV[0] & 255) && diff > -30 && diff < 0) {
                    late = 1;
                    lost = -1;
                    this.mDecryptIV[0] = (byte) ivbyte;
                    restore = true;
                } else if (ivbyte > (this.mDecryptIV[0] & 255) && diff > -30 && diff < 0) {
                    late = 1;
                    lost = -1;
                    this.mDecryptIV[0] = (byte) ivbyte;
                    for (i = 1; i < 16; i++) {
                        bArr2 = this.mDecryptIV;
                        b = bArr2[i];
                        bArr2[i] = (byte) (b - 1);
                        if (b != (byte) 0) {
                            break;
                        }
                    }
                    restore = true;
                } else if (ivbyte <= (this.mDecryptIV[0] & 255) || diff <= 0) {
                    if (ivbyte < (this.mDecryptIV[0] & 255) && diff > 0) {
                        lost = ((256 - (this.mDecryptIV[0] & 255)) + ivbyte) - 1;
                        this.mDecryptIV[0] = (byte) ivbyte;
                        for (i = 1; i < 16; i++) {
                            bArr2 = this.mDecryptIV;
                            b = (byte) (bArr2[i] + 1);
                            bArr2[i] = b;
                            if (b != (byte) 0) {
                                break;
                            }
                        }
                    } else {
                        bArr = null;
                    }
                } else {
                    lost = (ivbyte - this.mDecryptIV[0]) - 1;
                    this.mDecryptIV[0] = (byte) ivbyte;
                }
                if (this.mDecryptHistory[this.mDecryptIV[0] & 255] == this.mEncryptIV[0]) {
                    System.arraycopy(saveiv, 0, this.mDecryptIV, 0, 16);
                    bArr = null;
                }
            } else if (ivbyte > (this.mDecryptIV[0] & 255)) {
                this.mDecryptIV[0] = (byte) ivbyte;
            } else if (ivbyte < (this.mDecryptIV[0] & 255)) {
                this.mDecryptIV[0] = (byte) ivbyte;
                for (i = 1; i < 16; i++) {
                    bArr2 = this.mDecryptIV;
                    b = (byte) (bArr2[i] + 1);
                    bArr2[i] = b;
                    if (b != (byte) 0) {
                        break;
                    }
                }
            } else {
                bArr = null;
            }
            byte[] tagShiftedDst = new byte[plainLength];
            System.arraycopy(source, 4, tagShiftedDst, 0, plainLength);
            ocbDecrypt(tagShiftedDst, bArr, this.mDecryptIV, tag);
            if (tag[0] == source[1] && tag[1] == source[2] && tag[2] == source[3]) {
                this.mDecryptHistory[this.mDecryptIV[0] & 255] = this.mDecryptIV[1];
                if (restore) {
                    System.arraycopy(saveiv, 0, this.mDecryptIV, 0, 16);
                }
                this.mUiGood++;
                this.mUiLate += late;
                this.mUiLost += lost;
                this.mLastGoodStart = System.nanoTime();
            } else {
                System.arraycopy(saveiv, 0, this.mDecryptIV, 0, 16);
                bArr = null;
            }
        }
        return bArr;
    }

    public void ocbDecrypt(byte[] encrypted, byte[] plain, byte[] nonce, byte[] tag) throws BadPaddingException, IllegalBlockSizeException, ShortBufferException {
        byte[] checksum = new byte[16];
        byte[] tmp = new byte[16];
        byte[] delta = this.mEncryptCipher.doFinal(nonce);
        int offset = 0;
        int len = encrypted.length;
        while (len > 16) {
            byte[] buffer = new byte[16];
            CryptSupport.S2(delta);
            System.arraycopy(encrypted, offset, buffer, 0, 16);
            CryptSupport.XOR(tmp, delta, buffer);
            this.mDecryptCipher.doFinal(tmp, 0, 16, tmp);
            CryptSupport.XOR(buffer, delta, tmp);
            System.arraycopy(buffer, 0, plain, offset, 16);
            CryptSupport.XOR(checksum, checksum, buffer);
            len -= 16;
            offset += 16;
        }
        CryptSupport.S2(delta);
        CryptSupport.ZERO(tmp);
        long num = (long) (len * 8);
        tmp[14] = (byte) ((int) ((num >> 8) & 255));
        tmp[15] = (byte) ((int) (255 & num));
        CryptSupport.XOR(tmp, tmp, delta);
        byte[] pad = this.mEncryptCipher.doFinal(tmp);
        CryptSupport.ZERO(tmp);
        System.arraycopy(encrypted, offset, tmp, 0, len);
        CryptSupport.XOR(tmp, tmp, pad);
        CryptSupport.XOR(checksum, checksum, tmp);
        System.arraycopy(tmp, 0, plain, offset, len);
        CryptSupport.S3(delta);
        CryptSupport.XOR(tmp, delta, checksum);
        this.mEncryptCipher.doFinal(tmp, 0, 16, tag);
    }

    public synchronized byte[] encrypt(byte[] source, int length) throws BadPaddingException, IllegalBlockSizeException, ShortBufferException {
        byte[] dst;
        byte[] tag = new byte[16];
        for (int i = 0; i < 16; i++) {
            byte[] bArr = this.mEncryptIV;
            byte b = (byte) (bArr[i] + 1);
            bArr[i] = b;
            if (b != (byte) 0) {
                break;
            }
        }
        dst = new byte[(length + 4)];
        ocbEncrypt(source, dst, length, this.mEncryptIV, tag);
        System.arraycopy(dst, 0, dst, 4, length);
        dst[0] = this.mEncryptIV[0];
        dst[1] = tag[0];
        dst[2] = tag[1];
        dst[3] = tag[2];
        return dst;
    }

    public void ocbEncrypt(byte[] plain, byte[] encrypted, int plainLength, byte[] nonce, byte[] tag) throws BadPaddingException, IllegalBlockSizeException, ShortBufferException {
        byte[] checksum = new byte[16];
        byte[] tmp = new byte[16];
        byte[] delta = this.mEncryptCipher.doFinal(nonce);
        int offset = 0;
        int len = plainLength;
        while (len > 16) {
            byte[] buffer = new byte[16];
            CryptSupport.S2(delta);
            System.arraycopy(plain, offset, buffer, 0, 16);
            CryptSupport.XOR(checksum, checksum, buffer);
            CryptSupport.XOR(tmp, delta, buffer);
            this.mEncryptCipher.doFinal(tmp, 0, 16, tmp);
            CryptSupport.XOR(buffer, delta, tmp);
            System.arraycopy(buffer, 0, encrypted, offset, 16);
            len -= 16;
            offset += 16;
        }
        CryptSupport.S2(delta);
        CryptSupport.ZERO(tmp);
        long num = (long) (len * 8);
        tmp[14] = (byte) ((int) ((num >> 8) & 255));
        tmp[15] = (byte) ((int) (255 & num));
        CryptSupport.XOR(tmp, tmp, delta);
        byte[] pad = this.mEncryptCipher.doFinal(tmp);
        System.arraycopy(plain, offset, tmp, 0, len);
        System.arraycopy(pad, len, tmp, len, 16 - len);
        CryptSupport.XOR(checksum, checksum, tmp);
        CryptSupport.XOR(tmp, pad, tmp);
        System.arraycopy(tmp, 0, encrypted, offset, len);
        CryptSupport.S3(delta);
        CryptSupport.XOR(tmp, delta, checksum);
        this.mEncryptCipher.doFinal(tmp, 0, 16, tag);
    }
}
