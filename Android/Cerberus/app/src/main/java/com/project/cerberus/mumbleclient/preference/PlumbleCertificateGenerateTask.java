package com.project.cerberus.mumbleclient.preference;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.project.cerberus.R;

import java.io.File;

public class PlumbleCertificateGenerateTask extends AsyncTask<Void, Void, File> {
    private Context context;
    private ProgressDialog loadingDialog;

    /* renamed from: com.project.cerberus.mumbleclient.preference.PlumbleCertificateGenerateTask$1 */
    class C02551 implements OnCancelListener {
        C02551() {
        }

        public void onCancel(DialogInterface arg0) {
            PlumbleCertificateGenerateTask.this.cancel(true);
        }
    }

    public PlumbleCertificateGenerateTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.loadingDialog = new ProgressDialog(this.context);
        this.loadingDialog.setIndeterminate(true);
        this.loadingDialog.setMessage(this.context.getString(R.string.generateCertProgress));
        this.loadingDialog.setOnCancelListener(new C02551());
        this.loadingDialog.show();
    }

    protected File doInBackground(Void... params) {
        try {

            return com.project.cerberus.mumbleclient.preference.PlumbleCertificateManager.generateCertificate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    protected void onPostExecute(File result) {
        super.onPostExecute(result);

        if (result != null) {
            Toast.makeText(this.context, this.context.getString(R.string.generateCertSuccess, new Object[]{result.getName()}), 0).show();
        } else {
            Toast.makeText(this.context, R.string.generateCertFailure, 0).show();
        }
        this.loadingDialog.dismiss();
    }
}
