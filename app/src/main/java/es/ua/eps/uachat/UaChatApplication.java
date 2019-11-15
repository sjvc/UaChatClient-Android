package es.ua.eps.uachat;

import android.app.Application;

import es.ua.eps.uachat.connection.base.IChatConnection;
import es.ua.eps.uachat.connection.base.data.ChatUser;
import es.ua.eps.uachat.connection.socketio.SocketIoChatConnection;

public class UaChatApplication extends Application {
    private final static String IP = "192.168.43.57";
    private final static int PORT = 8080;

    private IChatConnection mConnection;
    private ChatUser mUser;

    @Override
    public void onCreate() {
        super.onCreate();

        mConnection = new SocketIoChatConnection(this, IP, PORT);
        mUser = new ChatUser(mConnection.getClientId(), "Pepito"); // TODO: Coger de Shared Preferences
    }

    public IChatConnection getChatConnection() {
        return mConnection;
    }

    public ChatUser getChatUser() {
        return mUser;
    }
}
