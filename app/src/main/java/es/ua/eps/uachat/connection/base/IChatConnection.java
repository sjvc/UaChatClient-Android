package es.ua.eps.uachat.connection.base;

import es.ua.eps.uachat.connection.base.data.*;

public interface IChatConnection {
    void connect(ChatUser user);
    boolean isConnected();
    void disconnect();
    void sendMessage(ChatMessage message);
    void requestMessageList(ChatMessageListRequest request);
    void requestUserList();
    void setChatConnectionListener(IChatConnectionListener listener);
    String getClientId();
}
