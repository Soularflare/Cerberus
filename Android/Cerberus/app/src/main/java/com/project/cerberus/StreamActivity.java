package com.project.cerberus;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.project.cerberus.mumbleclient.Settings;
import com.project.cerberus.mumbleclient.channel.ChannelListFragment;
import com.project.cerberus.mumbleclient.db.DatabaseProvider;
import com.project.cerberus.mumbleclient.db.PlumbleDatabase;
import com.project.cerberus.mumbleclient.service.IPlumbleService;
import com.project.cerberus.mumbleclient.service.PlumbleService;
import com.project.cerberus.mumbleclient.util.JumbleServiceFragment;
import com.project.cerberus.mumbleclient.util.JumbleServiceProvider;


public class StreamActivity extends AppCompatActivity implements JumbleServiceProvider, DatabaseProvider{


    private IPlumbleService mService;
    Button start_button;
    Button stop_button;
    Button talk_button;
    boolean start_button_debounce_active = false;
    private Settings mSettings;
    private JumbleServiceProvider mServiceProvider;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((PlumbleService.PlumbleBinder) service).getService();
            mService.setSuppressNotifications(true);
//            mService.registerObserver(mObserver);
            mService.clearChatNotifications(); // Clear chat notifications on resume.
//            mDrawerAdapter.notifyDataSetChanged();
//
//            for (JumbleServiceFragment fragment : mServiceFragments)
//                fragment.setServiceBound(true);

            // Re-show server list if we're showing a fragment that depends on the service.
//            if(getSupportFragmentManager().findFragmentById(R.id.content_frame) instanceof JumbleServiceFragment &&
//                    !mService.isConnected()) {
//                loadDrawerFragment(DrawerAdapter.ITEM_FAVOURITES);
//            }
//            updateConnectionState(getService());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        mSettings = Settings.getInstance(this);
        Fragment fragment = new ChannelListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();


        start_button = (Button) findViewById(R.id.start);
        stop_button = (Button) findViewById(R.id.stop);
        talk_button = (Button) findViewById(R.id.Talk);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("https://cerberus.localtunnel.me/stream");



        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.loadUrl("https://cerberus.localtunnel.me/stream");
                if (start_button_debounce_active)
                    return;
                start_button_debounce_active = true;
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        start_button_debounce_active = false;
                    }
                }, 500);
                start_button.setClickable(false);

            }
        });
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.loadUrl("https://cerberus.localtunnel.me");
            }
        });
//        talk_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Mumble.UserState.Builder usb = Mumble.UserState.newBuilder();
//                usb.setSelfMute(true);
//                usb.setSelfDeaf(false);
//                getConnection().sendTCPMessage(usb.build(), JumbleTCPMessageType.UserState);

////                if(mSettings.isMuted() || mSettings.isDeafened()) {
////                    mSettings.setMutedAndDeafened(false, false);
////                    Log.v("meow","muted");
////                }
////                else{
////                    mSettings.setMutedAndDeafened(true, true);
////                    Log.v("meow"," not muted");
////                }
//
//                IJumbleSession session = mServiceProvider.getService().getSession();
//                IUser self = session.getSessionUser();
//                boolean muted = !self.isSelfMuted();
//                boolean deafened = !self.isSelfDeafened();
//                session.setSelfMuteDeafState(muted, deafened);
//                supportInvalidateOptionsMenu();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  new MenuInflater(this.getApplicationContext());
        inflater.inflate(R.menu.fragment_channel_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_deafen_button:

                // Do Activity menu item stuff here
                return false;

            case R.id.menu_mute_button:

                // Not implemented here
                return false;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    public IPlumbleService getService() {
        Log.v("meow", "meow1");
        return mService;
    }

    @Override
    public void addServiceFragment(JumbleServiceFragment fragment) {

    }

    @Override
    public void removeServiceFragment(JumbleServiceFragment fragment) {

    }

    @Override
    public PlumbleDatabase getDatabase() {
        return null;
    }

}
