package com.project.cerberus.mumbleclient.preference;

import android.os.Environment;

import com.project.cerberus.jumble.net.JumbleCertificateGenerator;

import org.spongycastle.operator.OperatorCreationException;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlumbleCertificateManager {
    private static final String CERTIFICATE_FOLDER = "Plumble";
    private static final String CERTIFICATE_FORMAT = "plumble-%s.p12";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    /* renamed from: com.project.cerberus.mumbleclient.preference.PlumbleCertificateManager$1 */
    static class C02561 implements FileFilter {
        C02561() {
        }

        public boolean accept(File pathname) {
            return pathname.getName().endsWith("pfx") || pathname.getName().endsWith("p12");
        }
    }

    public static File generateCertificate() throws NoSuchAlgorithmException, OperatorCreationException, CertificateException, KeyStoreException, NoSuchProviderException, IOException {
        File certificateDirectory = getCertificateDirectory();



        String date = DATE_FORMAT.format(new Date());
        File certificateFile = new File(certificateDirectory, String.format(Locale.US, CERTIFICATE_FORMAT, new Object[]{date}));

        JumbleCertificateGenerator.generateCertificate(new FileOutputStream(certificateFile));
        return certificateFile;
    }

    public static List<File> getAvailableCertificates() throws IOException {
        return Arrays.asList(getCertificateDirectory().listFiles(new C02561()));
    }

    public static boolean isPasswordRequired(File certificateFile) throws KeyStoreException, IOException, NoSuchAlgorithmException {
        try {
            KeyStore.getInstance("PKCS12").load(new FileInputStream(certificateFile), new char[0]);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        } catch (CertificateException e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public static boolean isPasswordValid(File certificateFile, String password) throws KeyStoreException, IOException, NoSuchAlgorithmException {
        try {
            KeyStore.getInstance("PKCS12").load(new FileInputStream(certificateFile), password.toCharArray());
            return true;
        } catch (CertificateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static File getCertificateDirectory() throws IOException {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File certificateDirectory = new File(Environment.getExternalStorageDirectory(), "Plumble");
            if (!certificateDirectory.exists()) {
                certificateDirectory.mkdir();
            }
            return certificateDirectory;
        }
        throw new IOException("External storage not available.");
    }
}
