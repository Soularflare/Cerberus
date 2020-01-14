package com.project.cerberus;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.InstanceIdResult;

// Created by batman on 3/4/18.


@SuppressWarnings("deprecation")
public class FcmInstanceIdService extends FirebaseInstanceIdService
{
    public void onTokenRefresh()
    {
        String recent_token = FirebaseInstanceId.getInstance().getToken();


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN), recent_token);
        editor.commit();

    }
}