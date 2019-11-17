package es.ua.eps.uachat.connection.base.data;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Calendar;
import java.util.Date;

/*
    Representa un mensaje del chat.
    Implementa IMessage para que pueda ser usada con ChatKit
 */
public class ChatMessage implements IMessage {
    private ChatUser mSrcUser;
    private ChatUser mDstUser;
    private String mMessage;
    private long mTimestamp;

    public ChatMessage() {
        mTimestamp = System.currentTimeMillis();
    }

    public ChatMessage(ChatUser srcUser, ChatUser dstUser, String message) {
        this();

        mSrcUser = srcUser;
        mDstUser = dstUser;
        mMessage = message;
    }

    @Override
    public String getId() {
        return String.valueOf(mTimestamp);
    }

    @Override
    public String getText() {
        return mMessage;
    }

    @Override
    public IUser getUser() {
        return mSrcUser;
    }

    @Override
    public Date getCreatedAt() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mTimestamp);

        return cal.getTime();
    }

    public ChatUser getDstUser() {
        return mDstUser;
    }

    public String getMessage() {
        return mMessage;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setUser(ChatUser user) {
        mSrcUser = user;
    }

    public void setDstUser(ChatUser user) {
        mDstUser = user;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }
}
