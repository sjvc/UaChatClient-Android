package es.ua.eps.uachat.connection.base;

import es.ua.eps.uachat.connection.base.data.*;

/**
    Esta interaz define los métodos necesarios para gestionar la conexión con el servidor de Chat.
    La app trabajará con esta interfaz en lugar de con una implementación en concreto. Por lo que,
    si en un futuro quisiéremos cambiar la forma de realizar la conexión, solo habría que cambiar
    la implementación, y no tendríamos que tocar nada más de la aplicación.

    No hay método para hacer LogOut. Para hacer LogOut, basta con desconectar.
 */

public interface IChatConnection {
    void connect();
    boolean isConnected();
    void disconnect();
    void login(ChatUser user);
    boolean isLoggedIn();
    void sendMessage(ChatMessage message);
    void requestMessageList(ChatMessageListRequest request);
    void requestUserList();
    void addListener(IChatConnectionListener listener);
    void removeListener(IChatConnectionListener listener);
    String getClientId();
}
