package com.project.cerberus.mumbleclient.servers;

import android.content.Context;
import android.view.MenuItem;
import com.project.cerberus.jumble.model.Server;
import com.project.cerberus.R;
import java.util.List;

public class FavouriteServerAdapter extends ServerAdapter<Server> {
    private FavouriteServerAdapterMenuListener mListener;

    public interface FavouriteServerAdapterMenuListener {
        void deleteServer(Server server);

        void editServer(Server server);

        void shareServer(Server server);
    }

    public FavouriteServerAdapter(Context context, List<Server> servers, FavouriteServerAdapterMenuListener listener) {
        super(context, R.layout.server_list_row, servers);
        this.mListener = listener;
    }

    public int getPopupMenuResource() {
        return R.menu.popup_favourite_server;
    }

    public boolean onPopupItemClick(Server server, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
//            case R.id.menu_server_edit:
//                this.mListener.editServer(server);
//                return true;
//            case R.id.menu_server_share:
//                this.mListener.shareServer(server);
//                return true;
//            case R.id.menu_server_delete:
//                this.mListener.deleteServer(server);
//                return true;
            default:
                return false;
        }
    }
}
