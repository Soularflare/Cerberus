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
        server = new Server(-1, "User", "IP-address", 64738, "User", "hackme");             //change IP-address to your external IP



        mDatabase.addServer(server);




        new com.project.cerberus.mumbleclient.app.ServerConnectTask(this, this.mDatabase).execute(server);




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






    @Override
    public PlumbleDatabase getDatabase() {
        return null;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("Permissions", "Permission RECORD_AUDIO was given!");
                } else {

                    Log.d("Permissions", "Permission RECORD_AUDIO was NOT given!");
                }
                return;
            }

        }
    }
}



