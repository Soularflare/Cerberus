package com.project.cerberus.mumbleclient.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.project.cerberus.jumble.IJumbleService;
import com.project.cerberus.jumble.util.IJumbleObserver;
import com.project.cerberus.mumbleclient.service.IPlumbleService;

/**
 * Fragment class intended to make binding the Jumble service to fragments easier.
 * Created by andrew on 04/08/13.
 */
public abstract class JumbleServiceFragment extends Fragment {

    private JumbleServiceProvider mServiceProvider;

    /** State boolean to make sure we don't double initialize a fragment once a service has been bound. */
    private boolean mBound;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {

            mServiceProvider = (JumbleServiceProvider) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement JumbleServiceProvider");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mServiceProvider.addServiceFragment(this);
        if(mServiceProvider.getService() != null && !mBound)
            onServiceAttached(mServiceProvider.getService());
    }

    @Override
    public void onDestroy() {
        mServiceProvider.removeServiceFragment(this);
        if(mServiceProvider.getService() != null && mBound)
            onServiceDetached(mServiceProvider.getService());
        super.onDestroy();
    }

    /** The definitive place where data from the service will be used to initialize the fragment. Only called once per bind, whether the fragment loads first or the service. */
    public void onServiceBound(IJumbleService service) { }

    public void onServiceUnbound() { }

    /** If implemented, will register the returned observer to the service upon binding. */
    public IJumbleObserver getServiceObserver() {
        return null;
    }

    private void onServiceAttached(IJumbleService service) {
        mBound = true;
        if(getServiceObserver() != null)
            service.registerObserver(getServiceObserver());

        onServiceBound(service);
    }

    private void onServiceDetached(IJumbleService service) {
        mBound = false;
        if(getServiceObserver() != null)
            service.unregisterObserver(getServiceObserver());

        onServiceUnbound();
    }

    public void setServiceBound(boolean bound) {
        if(bound && !mBound)
            onServiceAttached(mServiceProvider.getService());
        else if(mBound && !bound)
            onServiceDetached(mServiceProvider.getService());
    }

    public IPlumbleService getService() {
        Log.v("meow", "meow");
        return mServiceProvider.getService();
    }
}