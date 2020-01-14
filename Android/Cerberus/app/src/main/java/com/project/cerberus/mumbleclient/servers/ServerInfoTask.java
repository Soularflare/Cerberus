package com.project.cerberus.mumbleclient.servers;

import android.os.AsyncTask;
import android.util.Log;
import ch.boye.httpclientandroidlib.impl.client.cache.CacheConfig;
import com.project.cerberus.jumble.model.Server;
import com.project.cerberus.mumbleclient.Constants;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class ServerInfoTask extends AsyncTask<Server, Void, ServerInfoResponse> {
    private Server server;

    protected ServerInfoResponse doInBackground(Server... params) {
        this.server = params[0];
        try {
            InetAddress host = InetAddress.getByName(this.server.getHost());
            ByteBuffer buffer = ByteBuffer.allocate(12);
            buffer.putInt(0);
            buffer.putLong(this.server.getId());
            DatagramPacket requestPacket = new DatagramPacket(buffer.array(), 12, host, this.server.getPort());
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(CacheConfig.DEFAULT_MAX_CACHE_ENTRIES);
            socket.setReceiveBufferSize(1024);
            long startTime = System.nanoTime();
            socket.send(requestPacket);
            byte[] responseBuffer = new byte[24];
            socket.receive(new DatagramPacket(responseBuffer, responseBuffer.length));
            ServerInfoResponse response = new ServerInfoResponse(this.server, responseBuffer, (int) ((System.nanoTime() - startTime) / 1000000));
            Log.i(Constants.TAG, "DEBUG: Server version: " + response.getVersionString() + "\nUsers: " + response.getCurrentUsers() + "/" + response.getMaximumUsers());
            return response;
        } catch (Exception e) {
            return new ServerInfoResponse();
        }
    }
}
