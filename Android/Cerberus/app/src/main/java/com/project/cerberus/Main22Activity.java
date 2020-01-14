package com.project.cerberus;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.cerberus.jumble.model.Server;
import com.project.cerberus.mumbleclient.Settings;
import com.project.cerberus.mumbleclient.db.DatabaseProvider;
import com.project.cerberus.mumbleclient.db.PlumbleDatabase;
import com.project.cerberus.mumbleclient.db.PlumbleSQLiteDatabase;
import com.project.cerberus.mumbleclient.service.PlumbleService.PlumbleBinder;


public class Main22Activity extends AppCompatActivity implements  DatabaseProvider {

    private DatabaseProvider mDatabaseProvider;
    private PlumbleBinder mService;
    private Settings mSettings;
    private ProgressDialog mConnectingDialog;
    private PlumbleDatabase mDatabase;
    private Server server;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        this.mDatabase = new PlumbleSQLiteDatabase(this);
        this.mDatabase.open();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 2);
        }
        Button Streambtn = findViewById(R.id.stream_act);
        Button Tokenbtn = findViewById(R.id.token_act);
        server = new Server(-1, "User", "IP-address", 64738, "User", "hackme");
//        this.mDatabaseProvider = (DatabaseProvider) this;


        mDatabase.addServer(server);

//       server =  ServerEditFragment.createServer();         //send server obj as param?


        new com.project.cerberus.mumbleclient.app.ServerConnectTask(this, this.mDatabase).execute(server);

        //this.dismiss();       #dismisses dialog box


        Streambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main22Activity.this, StreamActivity.class));
            }
        });
        Tokenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main22Activity.this, MainActivity.class));
            }
        });
    }


//    private Server getServer() {
//        return getArguments() != null ? (Server) getArguments().getParcelable("server") : null;
//    }

//    public Server createServer() {
//        int port;
//        Server server;
//        String name = "User";
//        String host = "IP-address";
//        port = 64738;
//        String username = "User";
//        String password = "hackme";
//
////        if (getServer() != null) {
////            server = getServer();
////            server.setName(name);
////            server.setHost(host);
////            server.setPort(port);
////            server.setUsername(username);
////            server.setPassword(password);
////            if (shouldCommit) {
////                this.mDatabaseProvider.getDatabase().updateServer(server);
////            }
////        } else {
//        server = new Server(-1, name, host, port, username, password);
////            if (shouldCommit) {
//        this.mDatabaseProvider.getDatabase().addServer(server);
////            }
//
////        if (shouldCommit) {
////            this.mListener.serverInfoUpdated();
////        }
//        return server;
//    }



    @Override
    public PlumbleDatabase getDatabase() {
        return null;
    }


//    private void generateCertificate() {
//        final Settings settings = Settings.getInstance(this);
//        new PlumbleCertificateGenerateTask(this) {
//            protected void onPostExecute(File result) {
//                super.onPostExecute(result);
//                settings.setCertificatePath(result.getAbsolutePath());
////                com.morlunk.mumbleclient.wizard.WizardCertificateFragment.this.mGenerateButton.setEnabled(false);
////                com.morlunk.mumbleclient.wizard.WizardCertificateFragment.this.mNavigation.next();
//            }
//        }.execute(new Void[0]);
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
//                    generateCertificate();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    Log.d("Permissions", "Permission RECORD_AUDIO was given. Nice!");
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("Permissions", "Permission RECORD_AUDIO was NOT given. D'oh!");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}



