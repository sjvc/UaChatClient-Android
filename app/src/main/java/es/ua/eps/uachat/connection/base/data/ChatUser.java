package es.ua.eps.uachat.connection.base.data;

public class ChatUser {
    protected String mId;
    protected String mName;
    protected boolean mIsConnected = false;

    public ChatUser() {

    }

    public ChatUser(String id, String name) {
        mId = id;
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    public void setIsConnected(boolean isConnected) {
        mIsConnected = isConnected;
    }
}
