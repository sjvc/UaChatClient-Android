package es.ua.eps.uachat.connection.base;

import es.ua.eps.uachat.connection.base.data.ChatMessage;
import es.ua.eps.uachat.connection.base.data.ChatUser;

// Interfaz que debe implementar cualquiera que use la clase IChatConnection
// para recibir información de la conexión con el servidor
public interface IChatConnectionListener {
    // Conexión
    void onConnected();
    void onConnectionError();
    void onConnectionTimeOut();

    // Mensajes recibidos por el servidor
    void onMessageListReceived(ChatMessage[] list);
    void onMessageReceived(ChatMessage message);
    void onUserListReceived(ChatUser[] list);
}
