package es.ua.eps.uachat.connection.base.data;

import com.stfalcon.chatkit.commons.models.IUser;

public class ChatUser implements IUser {
    protected String mId;
    protected String mName;
    protected boolean mIsConnected = false;

    public ChatUser() {

    }

    public ChatUser(String id, String name) {
        mId = id;
        mName = name;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getAvatar() {
        return null;
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    public void setIsConnected(boolean isConnected) {
        mIsConnected = isConnected;
    }
}