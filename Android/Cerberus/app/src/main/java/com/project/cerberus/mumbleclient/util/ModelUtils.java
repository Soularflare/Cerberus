package com.project.cerberus.mumbleclient.util;

import com.project.cerberus.jumble.model.IChannel;

import java.util.LinkedList;
import java.util.List;

/**
 * Tools for dealing with the recursive user-channel hierarchy.
 * Created by andrew on 18/10/14.
 */
public class ModelUtils {
    /**
     * Flattens the channel hierarchy, returning an array of channels in hierarchical order.
     * @param channel The root channel to flatten from.
     * @return A list of channels.
     */
    public static List<IChannel> getChannelList(IChannel channel) {
        LinkedList<IChannel> channels = new LinkedList<IChannel>();
        getChannelList(channel, channels);
        return channels;
    }

    private static void getChannelList(IChannel channel, List<IChannel> channels) {
        channels.add(channel);
        for (IChannel subc : channel.getSubchannels()) {
            if (subc != null) {
                getChannelList(subc, channels);
            }
        }
    }
}