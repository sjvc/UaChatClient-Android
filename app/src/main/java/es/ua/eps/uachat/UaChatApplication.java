package es.ua.eps.uachat;

import android.app.Application;
import es.ua.eps.uachat.connection.base.IChatConnection;
import es.ua.eps.uachat.connection.base.data.ChatUser;
import es.ua.eps.uachat.connection.socketio.SocketIoChatConnection;
import es.ua.eps.uachat.persistence.SharedPrefs;

/*
    Creamos aquí el objeto para gestionar la conexión y el que contiene los datos del usuario.
    Así podemos acceder a ellos desde cualquier parte.
 */
public class UaChatApplication extends Application {
    private IChatConnection mConnection;
    private ChatUser mUser;

    @Override
    public void onCreate() {
        super.onCreate();

        String ip = getResources().getString(R.string.connection_ip);
        int port = getResources().getInteger(R.integer.connection_port);

        mConnection = new SocketIoChatConnection(this, ip, port);
        mUser = new ChatUser(mConnection.getClientId(), SharedPrefs.getUserName(this));
    }

    public IChatConnection getChatConnection() {
        return mConnection;
    }

    public ChatUser getChatUser() {
        return mUser;
    }
}
