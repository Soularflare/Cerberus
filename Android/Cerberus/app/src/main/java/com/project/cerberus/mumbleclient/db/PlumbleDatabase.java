package com.project.cerberus.mumbleclient.db;

import com.project.cerberus.jumble.model.Server;

import java.util.List;

/**
 * An interface for persistent storage services (i.e. databases, cloud) to implement.
 * Created by andrew on 13/08/13.
 */
public interface PlumbleDatabase {
    public void open();
    public void close();

    public List<Server> getServers();
    public void addServer(Server server);
    public void updateServer(Server server);
    public void removeServer(Server server);

    public boolean isCommentSeen(String hash, byte[] commentHash);
    public void markCommentSeen(String hash, byte[] commentHash);

    public List<Integer> getPinnedChannels(long serverId);
    public void addPinnedChannel(long serverId, int channelId);
    public void removePinnedChannel(long serverId, int channelId);
    public boolean isChannelPinned(long serverId, int channelId);

    public List<String> getAccessTokens(long serverId);
    public void addAccessToken(long serverId, String token);
    public void removeAccessToken(long serverId, String token);

    public List<Integer> getLocalMutedUsers(long serverId);
    public void addLocalMutedUser(long serverId, int userId);
    public void removeLocalMutedUser(long serverId, int userId);

    public List<Integer> getLocalIgnoredUsers(long serverId);
    public void addLocalIgnoredUser(long serverId, int userId);
    public void removeLocalIgnoredUser(long serverId, int userId);

    /**
     * Adds the given certificate binary blob to the database.
     * @param name The user-readable certificate name.
     * @param certificate A PKCS12 binary blob.
     * @return A handle for the newly craeted certificate.
     */
    DatabaseCertificate addCertificate(String name, byte[] certificate);
    List<DatabaseCertificate> getCertificates();

    /**
     * Obtains the certificate data associated with the given certificate ID.
     * @param id The certificate ID to fetch the data of.
     * @return A binary representation of a PKCS12 certificate.
     */
    byte[] getCertificateData(long id);

    /**
     * Removes the certificate with the given ID.
     * @param id The certificate's identifier.
     */
    void removeCertificate(long id);
}