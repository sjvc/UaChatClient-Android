package es.ua.eps.uachat.connection.base;

import es.ua.eps.uachat.connection.base.data.*;

/*
    Esta interaz define los métodos necesarios para gestionar la conexión con el servidor de Chat.
    La app trabajará con esta interfaz en lugar de con una implementación en concreto. Por lo que,
    si en un futuro quisiéremos cambiar la forma de realizar la conexión, solo habría que cambiar
    la implementación, y no tendríamos que tocar nada más de la aplicación.
 */

public interface IChatConnection {
    void connect(ChatUser user);
    boolean isConnected();
    void disconnect();
    void sendMessage(ChatMessage message);
    void requestMessageList(ChatMessageListRequest request);
    void requestUserList();
    void addListener(IChatConnectionListener listener);
    void removeListener(IChatConnectionListener listener);
    String getClientId();
}
