package es.ua.eps.uachat.connection.base.data;

import com.stfalcon.chatkit.commons.models.IUser;

/*
    Representa un usuario del chat.
    Implementa IUser para que pueda ser usada con ChatKit
 */
public class ChatUser implements IUser {
    private String mId;
    private String mName;
    private boolean mIsConnected = false;

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

    public void setId(String id) {
        mId = id;
    }

    public void setIsConnected(boolean isConnected) {
        mIsConnected = isConnected;
    }

    public void setName(String name) {
        mName = name;
    }
}
