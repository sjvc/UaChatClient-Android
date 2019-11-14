package es.ua.eps.uachat.connection;

import es.ua.eps.uachat.serverdata.ChatMessage;

public interface IOnMessageReceivedEvent {
    void onMessageReceived(ChatMessage message);
}
