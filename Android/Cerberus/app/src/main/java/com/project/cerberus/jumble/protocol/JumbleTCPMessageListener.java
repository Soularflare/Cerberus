package com.project.cerberus.jumble.protocol;

import com.project.cerberus.jumble.protobuf.Mumble.ACL;
import com.project.cerberus.jumble.protobuf.Mumble.Authenticate;
import com.project.cerberus.jumble.protobuf.Mumble.BanList;
import com.project.cerberus.jumble.protobuf.Mumble.ChannelRemove;
import com.project.cerberus.jumble.protobuf.Mumble.ChannelState;
import com.project.cerberus.jumble.protobuf.Mumble.CodecVersion;
import com.project.cerberus.jumble.protobuf.Mumble.ContextAction;
import com.project.cerberus.jumble.protobuf.Mumble.ContextActionModify;
import com.project.cerberus.jumble.protobuf.Mumble.CryptSetup;
import com.project.cerberus.jumble.protobuf.Mumble.PermissionDenied;
import com.project.cerberus.jumble.protobuf.Mumble.PermissionQuery;
import com.project.cerberus.jumble.protobuf.Mumble.Ping;
import com.project.cerberus.jumble.protobuf.Mumble.QueryUsers;
import com.project.cerberus.jumble.protobuf.Mumble.Reject;
import com.project.cerberus.jumble.protobuf.Mumble.RequestBlob;
import com.project.cerberus.jumble.protobuf.Mumble.ServerConfig;
import com.project.cerberus.jumble.protobuf.Mumble.ServerSync;
import com.project.cerberus.jumble.protobuf.Mumble.SuggestConfig;
import com.project.cerberus.jumble.protobuf.Mumble.TextMessage;
import com.project.cerberus.jumble.protobuf.Mumble.UDPTunnel;
import com.project.cerberus.jumble.protobuf.Mumble.UserList;
import com.project.cerberus.jumble.protobuf.Mumble.UserRemove;
import com.project.cerberus.jumble.protobuf.Mumble.UserState;
import com.project.cerberus.jumble.protobuf.Mumble.UserStats;
import com.project.cerberus.jumble.protobuf.Mumble.Version;
import com.project.cerberus.jumble.protobuf.Mumble.VoiceTarget;

public interface JumbleTCPMessageListener {

    public static class Stub implements JumbleTCPMessageListener {
        public void messageAuthenticate(Authenticate msg) {
        }

        public void messageBanList(BanList msg) {
        }

        public void messageReject(Reject msg) {
        }

        public void messageServerSync(ServerSync msg) {
        }

        public void messageServerConfig(ServerConfig msg) {
        }

        public void messagePermissionDenied(PermissionDenied msg) {
        }

        public void messageUDPTunnel(UDPTunnel msg) {
        }

        public void messageUserState(UserState msg) {
        }

        public void messageUserRemove(UserRemove msg) {
        }

        public void messageChannelState(ChannelState msg) {
        }

        public void messageChannelRemove(ChannelRemove msg) {
        }

        public void messageTextMessage(TextMessage msg) {
        }

        public void messageACL(ACL msg) {
        }

        public void messageQueryUsers(QueryUsers msg) {
        }

        public void messagePing(Ping msg) {
        }

        public void messageCryptSetup(CryptSetup msg) {
        }

        public void messageContextAction(ContextAction msg) {
        }

        public void messageContextActionModify(ContextActionModify msg) {
        }

        public void messageRemoveContextAction(ContextActionModify msg) {
        }

        public void messageVersion(Version msg) {
        }

        public void messageUserList(UserList msg) {
        }

        public void messagePermissionQuery(PermissionQuery msg) {
        }

        public void messageCodecVersion(CodecVersion msg) {
        }

        public void messageUserStats(UserStats msg) {
        }

        public void messageRequestBlob(RequestBlob msg) {
        }

        public void messageSuggestConfig(SuggestConfig msg) {
        }

        public void messageVoiceTarget(VoiceTarget msg) {
        }
    }

    void messageACL(ACL acl);

    void messageAuthenticate(Authenticate authenticate);

    void messageBanList(BanList banList);

    void messageChannelRemove(ChannelRemove channelRemove);

    void messageChannelState(ChannelState channelState);

    void messageCodecVersion(CodecVersion codecVersion);

    void messageContextAction(ContextAction contextAction);

    void messageContextActionModify(ContextActionModify contextActionModify);

    void messageCryptSetup(CryptSetup cryptSetup);

    void messagePermissionDenied(PermissionDenied permissionDenied);

    void messagePermissionQuery(PermissionQuery permissionQuery);

    void messagePing(Ping ping);

    void messageQueryUsers(QueryUsers queryUsers);

    void messageReject(Reject reject);

    void messageRemoveContextAction(ContextActionModify contextActionModify);

    void messageRequestBlob(RequestBlob requestBlob);

    void messageServerConfig(ServerConfig serverConfig);

    void messageServerSync(ServerSync serverSync);

    void messageSuggestConfig(SuggestConfig suggestConfig);

    void messageTextMessage(TextMessage textMessage);

    void messageUDPTunnel(UDPTunnel uDPTunnel);

    void messageUserList(UserList userList);

    void messageUserRemove(UserRemove userRemove);

    void messageUserState(UserState userState);

    void messageUserStats(UserStats userStats);

    void messageVersion(Version version);

    void messageVoiceTarget(VoiceTarget voiceTarget);
}
