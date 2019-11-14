package es.ua.eps.uachat;

import android.app.Application;
import android.os.IBinder;

import es.ua.eps.uachat.connection.IChatConnection;
import es.ua.eps.uachat.connection.SocketIoChatConnection;

public class UaChatApplication extends Application {
    private final static String IP = "192.168.43.57";
    private final static int PORT = 8080;

    private IChatConnection mConnection;

    @Override
    public void onCreate() {
        super.onCreate();

        mConnection = new SocketIoChatConnection(IP, PORT);
    }

    public IChatConnection getChatConnection() {
        return mConnection;
    }
}
