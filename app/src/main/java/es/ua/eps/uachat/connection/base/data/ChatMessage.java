package es.ua.eps.uachat.connection.base.data;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Calendar;
import java.util.Date;

public class ChatMessage implements IMessage {
    protected ChatUser mSrcUser;
    protected ChatUser mDstUser;
    protected String mMessage;
    protected long mTimestamp;

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

    public long getTimestamp() {
        return mTimestamp;
    }
}
