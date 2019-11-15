package es.ua.eps.uachat.connection.base.data;

public class ChatMessageListRequest {

    protected String mSrcUserId;
    protected String mDstUserId;
    protected long mTimestamp;

    public ChatMessageListRequest() {

    }

    public ChatMessageListRequest(String srcUserId, String dstUserId, long timestamp) {
        mSrcUserId = srcUserId;
        mDstUserId = dstUserId;
        mTimestamp = timestamp;
    }

    public String getSrcUserId() {
        return mSrcUserId;
    }

    public String getDstUserId() {
        return mDstUserId;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

}
