package es.ua.eps.uachat.connection;

import android.util.Log;

import java.net.URISyntaxException;

import es.ua.eps.uachat.serverdata.ChatMessage;
import es.ua.eps.uachat.serverdata.MessageListRequest;
import es.ua.eps.uachat.serverdata.User;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketIoChatConnection implements IChatConnection {
    private final static String DEBUG = "UaChat";

    private final static String EVENT_USER_HELLO = "hello";
    private final static String EVENT_SEND_MESSAGE = "sendMessage";
    private final static String EVENT_REQUEST_MESSAGE_LIST = "getMessageList";
    private final static String EVENT_REQUEST_USER_LIST = "getUserList";
    private final static String ON_EVENT_MESSAGE_LIST_REVEICED = "onMessageListReceived";
    private final static String ON_EVENT_USER_LIST_RECEIVED = "onUserListReceived";
    private final static String ON_EVENT_MESSAGE_RECEIVED = "onMessageReceived";

    private Socket mSocket;
    private String mIp;
    private int mPort;

    private IOnMessageListReceivedEvent mOnMessageListReceived;
    private IOnUserListReceivedEvent mOnUserListReceived;
    private IOnMessageReceivedEvent mOnMessageReceived;

    public SocketIoChatConnection(String ip, int port) {
        mIp = ip;
        mPort = port;
    }

    @Override
    public void connect(final User hello, final IOnConnectionResult result) {
        Log.v(DEBUG, "Conectando... a http://" + mIp + ":" + mPort);

        try {
            mSocket = IO.socket("http://" + mIp + ":" + mPort);

            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v(DEBUG, "onConnected");
                    mSocket.emit(EVENT_USER_HELLO, hello);
                    if (result != null) result.onConnected();
                }
            });

            mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v(DEBUG, "onError");
                    if (result != null) result.onError();
                }
            });

            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v(DEBUG, "onConnectTimeout");
                    if (result != null) result.onTimeOut();
                }
            });

            mSocket.on(ON_EVENT_MESSAGE_LIST_REVEICED, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if(mOnMessageListReceived != null) {
                        mOnMessageListReceived.onMessageListReceived((ChatMessage[]) args[0]);
                    }
                }
            });

            mSocket.on(ON_EVENT_USER_LIST_RECEIVED, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if(mOnUserListReceived != null) {
                        mOnUserListReceived.onUserListReceived((User[]) args[0]);
                    }
                }
            });

            mSocket.on(ON_EVENT_MESSAGE_RECEIVED, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (mOnMessageReceived != null) {
                        mOnMessageReceived.onMessageReceived((ChatMessage)args[0]);
                    }
                }
            });

            mSocket.connect();
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        mSocket.disconnect();
    }

    @Override
    public void sendMessage(ChatMessage message) {
        mSocket.emit(EVENT_SEND_MESSAGE, message);
    }

    @Override
    public void requestMessageList(MessageListRequest request, IOnMessageListReceivedEvent callback) {
        mOnMessageListReceived = callback;
        mSocket.emit(EVENT_REQUEST_MESSAGE_LIST);
    }

    @Override
    public void requestUserList(IOnUserListReceivedEvent callback) {
        mOnUserListReceived = callback;
        mSocket.emit(EVENT_REQUEST_USER_LIST, getClientId());
    }

    @Override
    public void onMessageReceived(IOnMessageReceivedEvent callback) {
        mOnMessageReceived = callback;
    }

    @Override
    public String getClientId() {
        return "FGSDKJGSDJK";
    }
}
