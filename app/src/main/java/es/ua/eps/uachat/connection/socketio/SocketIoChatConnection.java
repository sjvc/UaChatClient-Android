package es.ua.eps.uachat.connection.socketio;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import es.ua.eps.uachat.connection.base.IChatConnection;
import es.ua.eps.uachat.connection.base.IChatConnectionListener;
import es.ua.eps.uachat.connection.base.data.*;
import es.ua.eps.uachat.connection.socketio.data.*;
import es.ua.eps.uachat.util.AndroidLoggingHandler;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/*
    Esta clase implementa los métodos de IChatConnection para que funcione con Socket.IO.
    El funcionamiento es muy sencillo: Realiza una conexión a una IP y un puerto (un WebSocket), y
    lo único que hacemos es "traducir" las llamadas y eventos de Socket.IO a IChatConnection
 */
public class SocketIoChatConnection implements IChatConnection {
    private final static String DEBUG = "UaChat";

    private final static String EVENT_USER_LOGIN = "login";
    private final static String EVENT_SEND_MESSAGE = "sendMessage";
    private final static String EVENT_REQUEST_MESSAGE_LIST = "getMessageList";
    private final static String EVENT_REQUEST_USER_LIST = "getUserList";
    private final static String ON_EVENT_MESSAGE_LIST_RECEIVED = "onMessageListReceived";
    private final static String ON_EVENT_USER_LIST_RECEIVED = "onUserListReceived";
    private final static String ON_EVENT_MESSAGE_RECEIVED = "onMessageReceived";
    private final static String ON_EVENT_LOGGED_IN = "onLoggedIn";

    private WeakReference<Context> mAppContext; // Weak para no causar "Memory leaks" almacenando un Context evitando que se libere
    private Socket mSocket;
    private String mIp;
    private int mPort;

    private IChatConnectionListener mListener;

    public SocketIoChatConnection(Context context, String ip, int port) {
        mAppContext = new WeakReference<>(context.getApplicationContext());
        mIp = ip;
        mPort = port;
    }

    @Override
    public void connect(final ChatUser user) {
        Log.v(DEBUG, "Conectando... a http://" + mIp + ":" + mPort);

        if (isConnected()) {
            Log.v(DEBUG, "Ya estábamos conectados al servidor");
            if (mListener != null) mListener.onConnected();
            return;
        }

        // Mostrar log
        AndroidLoggingHandler.reset(new AndroidLoggingHandler());
        java.util.logging.Logger.getLogger(Socket.class.getName()).setLevel(Level.FINEST);
        java.util.logging.Logger.getLogger(io.socket.engineio.client.Socket.class.getName()).setLevel(Level.FINEST);
        java.util.logging.Logger.getLogger(Manager.class.getName()).setLevel(Level.FINEST);

        try {
            mSocket = IO.socket("http://" + mIp + ":" + mPort);

            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v(DEBUG, "EVENT_CONNECT");
                    mSocket.emit(EVENT_USER_LOGIN, ((JsonChatUser)user).toJSON());
                    if (mListener != null) mListener.onConnected();
                }
            });

            mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v(DEBUG, "EVENT_CONNECT_ERROR");
                    if (mListener != null) mListener.onConnectionError();
                }
            });

            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v(DEBUG, "EVENT_CONNECT_TIMEOUT");
                    if (mListener != null) mListener.onConnectionTimeOut();
                }
            });

            mSocket.on(ON_EVENT_MESSAGE_LIST_RECEIVED, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v(DEBUG, "ON_EVENT_MESSAGE_LIST_RECEIVED");

                    List<ChatMessage> messages = new ArrayList<>();
                    JSONArray array = (JSONArray)args[0];
                    for (int i=0; i<array.length(); i++) {
                        try {
                            messages.add(new JsonChatMessage().fromJSON((JSONObject)array.get(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (mListener != null) mListener.onMessageListReceived(messages);
                }
            });

            mSocket.on(ON_EVENT_USER_LIST_RECEIVED, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v(DEBUG, "ON_EVENT_USER_LIST_RECEIVED");

                    List<ChatUser> users = new ArrayList<>();
                    JSONArray array = (JSONArray)args[0];
                    for (int i=0; i<array.length(); i++) {
                        try {
                            users.add(new JsonChatUser().fromJSON((JSONObject)array.get(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (mListener != null) mListener.onUserListReceived(users);
                }
            });

            mSocket.on(ON_EVENT_MESSAGE_RECEIVED, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v(DEBUG, "ON_EVENT_MESSAGE_RECEIVED");
                    JsonChatMessage message = new JsonChatMessage().fromJSON((JSONObject)args[0]);
                    if (mListener != null) mListener.onMessageReceived(message);
                }
            });

            mSocket.on(ON_EVENT_LOGGED_IN, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v(DEBUG, "ON_EVENT_LOGGED_IN");
                    if (mListener != null) mListener.onLoggedIn();
                }
            });

            mSocket.connect();
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        return mSocket != null && mSocket.connected();
    }

    @Override
    public void disconnect() {
        if (isConnected()) {
            mSocket.disconnect();
        }
    }

    @Override
    public void sendMessage(ChatMessage message) {
        if (isConnected()) {
            mSocket.emit(EVENT_SEND_MESSAGE, ((JsonChatMessage)message).toJSON());
        }
    }

    @Override
    public void requestMessageList(ChatMessageListRequest request) {
        if (isConnected()) {
            mSocket.emit(EVENT_REQUEST_MESSAGE_LIST, ((JsonMessageListRequest)request).toJSON());
        }
    }

    @Override
    public void requestUserList() {
        if (isConnected()) {
            mSocket.emit(EVENT_REQUEST_USER_LIST, getClientId());
        }
    }

    @Override
    public void setChatConnectionListener(IChatConnectionListener listener) {
        mListener = listener;
    }

    @Override
    public String getClientId() {
        if (mAppContext.get() != null) {
            return Settings.Secure.getString(mAppContext.get().getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        return null;
    }
}
