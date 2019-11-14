package es.ua.eps.uachat.connection;

import es.ua.eps.uachat.serverdata.ChatMessage;
import es.ua.eps.uachat.serverdata.MessageListRequest;
import es.ua.eps.uachat.serverdata.User;

public interface IChatConnection {
    void connect(User hello, IOnConnectionResult result);
    void disconnect();
    void sendMessage(ChatMessage message);
    void requestMessageList(MessageListRequest request, IOnMessageListReceivedEvent callback);
    void requestUserList(IOnUserListReceivedEvent callback);
    void onMessageReceived(IOnMessageReceivedEvent callback);
    String getClientId();
}
