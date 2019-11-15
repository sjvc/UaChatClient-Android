package es.ua.eps.uachat.connection.base;

import java.util.List;

import es.ua.eps.uachat.connection.base.data.ChatMessage;
import es.ua.eps.uachat.connection.base.data.ChatUser;

// Interfaz que debe implementar cualquiera que use la clase IChatConnection
// para recibir información de la conexión con el servidor
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
