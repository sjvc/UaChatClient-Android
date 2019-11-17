package es.ua.eps.uachat.connection.base;

import java.util.List;

import es.ua.eps.uachat.connection.base.data.ChatMessage;
import es.ua.eps.uachat.connection.base.data.ChatUser;

/*
    La clase que implemente IChatConnection podrá comunicarse con cualquier clase
    que implemente IChatConnectionListener llamando a los métodos de esta última.
 */
public interface IChatConnectionListener {
    // Conexión
    void onConnected();
    void onConnectionError();
    void onConnectionTimeOut();
    void onLoggedIn();

    // Mensajes recibidos por el servidor
    void onMessageListReceived(List<ChatMessage> messages);
    void onMessageReceived(ChatMessage message);
    void onUserListReceived(List<ChatUser> users);
}
