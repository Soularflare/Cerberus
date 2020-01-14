package com.project.cerberus.mumbleclient.servers;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.project.cerberus.jumble.model.Server;
import com.project.cerberus.R;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ServerAdapter<E extends Server> extends ArrayAdapter<E> {
    private static final int MAX_ACTIVE_PINGS = 50;
    private ConcurrentHashMap<Server, ServerInfoResponse> mInfoResponses = new ConcurrentHashMap();
    private ExecutorService mPingExecutor = Executors.newFixedThreadPool(50);
    private int mViewResource;

    public abstract int getPopupMenuResource();

    public abstract boolean onPopupItemClick(Server server, MenuItem menuItem);

    public ServerAdapter(Context context, int viewResource, List<E> servers) {
        super(context, 0, servers);
        this.mViewResource = viewResource;
    }

    public long getItemId(int position) {
        return ((Server) getItem(position)).getId();
    }

//    public View getView(int position, View v, ViewGroup parent) {
//        View view = v;
//        if (v == null) {
//            view = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(this.mViewResource, parent, false);
//        }
//        final Server server = (Server) getItem(position);
//        ServerInfoResponse infoResponse = (ServerInfoResponse) this.mInfoResponses.get(server);
//        boolean requestExists = infoResponse != null;
//        boolean requestFailure = infoResponse != null && infoResponse.isDummy();
//        TextView userText = (TextView) view.findViewById(R.id.server_row_user);
//        TextView addressText = (TextView) view.findViewById(R.id.server_row_address);
//        ((TextView) view.findViewById(R.id.server_row_name)).setText(server.getName());
//        if (userText != null) {
//            userText.setText(server.getUsername());
//        }
//        if (addressText != null) {
//            addressText.setText(server.getHost() + ":" + server.getPort());
//        }
//        final ImageView moreButton = (ImageView) view.findViewById(R.id.server_row_more);
//        if (moreButton != null) {
//            moreButton.setOnClickListener(new OnClickListener() {
//                public void onClick(View v) {
//                    ServerAdapter.this.onServerOptionsClick(server, moreButton);
//                }
//            });
//        }
//        TextView serverVersionText = (TextView) view.findViewById(R.id.server_row_version_status);
//        TextView serverLatencyText = (TextView) view.findViewById(R.id.server_row_latency);
//        TextView serverUsersText = (TextView) view.findViewById(R.id.server_row_usercount);
//        ProgressBar serverInfoProgressBar = (ProgressBar) view.findViewById(R.id.server_row_ping_progress);
//        serverVersionText.setVisibility(!requestExists ? 4 : 0);
//        serverUsersText.setVisibility(!requestExists ? 4 : 0);
//        serverLatencyText.setVisibility(!requestExists ? 4 : 0);
//        serverInfoProgressBar.setVisibility(!requestExists ? 0 : 4);
//        if (infoResponse != null && !requestFailure) {
//            serverVersionText.setText(getContext().getString(R.string.online) + " (" + infoResponse.getVersionString() + ")");
//            serverUsersText.setText(infoResponse.getCurrentUsers() + "/" + infoResponse.getMaximumUsers());
//            serverLatencyText.setText(infoResponse.getLatency() + "ms");
//        } else if (requestFailure) {
//            serverVersionText.setText(R.string.offline);
//            serverUsersText.setText("");
//            serverLatencyText.setText("");
//        }
//        if (infoResponse == null) {
//            ServerInfoTask task = new ServerInfoTask() {
//                protected void onPostExecute(ServerInfoResponse result) {
//                    super.onPostExecute(result);
//                    ServerAdapter.this.mInfoResponses.put(server, result);
//                    ServerAdapter.this.notifyDataSetChanged();
//                }
//            };
//            if (VERSION.SDK_INT >= 11) {
//                task.executeOnExecutor(this.mPingExecutor, new Server[]{server});
//            } else {
//                task.execute(new Server[]{server});
//            }
//        }
//        return view;
//    }

    private void onServerOptionsClick(final Server server, View optionsButton) {
        PopupMenu popupMenu = new PopupMenu(getContext(), optionsButton);
        popupMenu.inflate(getPopupMenuResource());
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                return ServerAdapter.this.onPopupItemClick(server, menuItem);
            }
        });
        popupMenu.show();
    }
}
