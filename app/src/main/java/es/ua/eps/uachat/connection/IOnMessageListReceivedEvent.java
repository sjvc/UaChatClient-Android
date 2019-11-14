package es.ua.eps.uachat.connection;

import es.ua.eps.uachat.serverdata.ChatMessage;

public interface IOnMessageListReceivedEvent {
    void onMessageListReceived(ChatMessage[] list);
}
