package com.project.cerberus.jumble.net;

import android.util.Log;
import com.project.cerberus.jumble.Constants;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class JumbleSSLSocketFactory {
    private SSLContext mContext = SSLContext.getInstance("TLS");
    private JumbleTrustManagerWrapper mTrustWrapper;

    private static class JumbleTrustManagerWrapper implements X509TrustManager {
        private X509TrustManager mDefaultTrustManager;
        private X509Certificate[] mServerChain;
        private X509TrustManager mTrustManager;

        public JumbleTrustManagerWrapper(X509TrustManager trustManager) throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory dmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            dmf.init((KeyStore) null);
            this.mDefaultTrustManager = (X509TrustManager) dmf.getTrustManagers()[0];
            this.mTrustManager = trustManager;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                this.mDefaultTrustManager.checkClientTrusted(chain, authType);
            } catch (CertificateException e) {
                if (this.mTrustManager != null) {
                    this.mTrustManager.checkClientTrusted(chain, authType);
                    return;
                }
                throw e;
            }
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            this.mServerChain = chain;
            try {
                this.mDefaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException e) {
                if (this.mTrustManager != null) {
                    this.mTrustManager.checkServerTrusted(chain, authType);
                    return;
                }
                throw e;
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return this.mDefaultTrustManager.getAcceptedIssuers();
        }

        public X509Certificate[] getServerChain() {
            return this.mServerChain;
        }
    }

    public JumbleSSLSocketFactory(KeyStore keystore, String keystorePassword, String trustStorePath, String trustStorePassword, String trustStoreFormat) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException, NoSuchProviderException, IOException, CertificateException {
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
        kmf.init(keystore, keystorePassword != null ? keystorePassword.toCharArray() : new char[0]);

        if (trustStorePath != null) {
            KeyStore trustStore = KeyStore.getInstance(trustStoreFormat);

            trustStore.load(new FileInputStream(trustStorePath), trustStorePassword.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            this.mTrustWrapper = new JumbleTrustManagerWrapper((X509TrustManager) tmf.getTrustManagers()[0]);
            Log.i(Constants.TAG, "Using custom trust store " + trustStorePath + " with system trust store");
        } else {
            this.mTrustWrapper = new JumbleTrustManagerWrapper(null);
            Log.i(Constants.TAG, "Using system trust store");
        }
        this.mContext.init(kmf.getKeyManagers(), new TrustManager[]{this.mTrustWrapper}, null);
    }

    public SSLSocket createTorSocket(InetAddress host, int port, String proxyHost, int proxyPort) throws IOException {
        Socket socket = new Socket(new Proxy(Type.SOCKS, new InetSocketAddress(proxyHost, proxyPort)));
        socket.connect(new InetSocketAddress(host, port));
        return (SSLSocket) this.mContext.getSocketFactory().createSocket(socket, host.getHostName(), port, true);
    }

    public SSLSocket createSocket(InetAddress host, int port) throws IOException {

        SSLSocket socket = (SSLSocket) this.mContext.getSocketFactory().createSocket(host, port);

        return socket;
    }

    public X509Certificate[] getServerChain() {
        return this.mTrustWrapper.getServerChain();
    }
}
