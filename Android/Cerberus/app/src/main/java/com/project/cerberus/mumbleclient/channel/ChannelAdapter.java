package com.project.cerberus.mumbleclient.channel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.project.cerberus.jumble.model.IChannel;
import com.project.cerberus.jumble.model.IUser;
import com.project.cerberus.jumble.model.User;

/**
 * Simple adapter to display the users in a single channel.
 * Created by andrew on 24/11/13.
 */
public class ChannelAdapter extends BaseAdapter {

    private Context mContext;
    private IChannel mChannel;

    public ChannelAdapter(Context context, IChannel channel) {
        mContext = context;
        mChannel = channel;
    }

    @Override
    public int getCount() {
        return mChannel.getUsers().size();
    }

    @Override
    public Object getItem(int position) {
        return mChannel.getUsers().get(position);
    }

    @Override
    public long getItemId(int position) {
        IUser user = mChannel.getUsers().get(position);
        if (user != null)
            return user.getUserId();
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
//            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
//            v = layoutInflater.inflate(R.layout.overlay_user_row, parent, false);
        }
        User user = (User) getItem(position);
       // TextView titleView = (TextView) v.findViewById(R.id.user_row_name);
        //titleView.setText(user.getName());

       // ImageView state = (ImageView) v.findViewById(R.id.user_row_state);
//        if (user.isSelfDeafened())
//            state.setImageResource(R.drawable.ic_deafened);
//        else if (user.isSelfMuted())
//            state.setImageResource(R.drawable.ic_muted);
//        else if (user.isDeafened())
//            state.setImageResource(R.drawable.ic_server_deafened);
//        else if (user.isMuted())
//            state.setImageResource(R.drawable.ic_server_muted);
//        else if (user.isSuppressed())
//            state.setImageResource(R.drawable.ic_suppressed);
//        else
//        if (user.getTalkState() == TalkState.TALKING)
//            state.setImageResource(R.drawable.ic_talking_on);
//        else
//            state.setImageResource(R.drawable.ic_talking_off);

        return v;
    }

    public void setChannel(IChannel channel) {
        mChannel = channel;
        notifyDataSetChanged();
    }

    public IChannel getChannel() {
        return mChannel;
    }
}