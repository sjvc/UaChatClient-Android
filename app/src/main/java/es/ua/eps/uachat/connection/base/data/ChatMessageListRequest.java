package es.ua.eps.uachat.connection.base.data;

/*
    Encapsula una petición al servidor de la lista de usuarios.
 */
public class ChatMessageListRequest {

    private String mSrcUserId;
    private String mDstUserId;
    private long mTimestamp;

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

    public void setSrcUserId(String userId) {
        mSrcUserId = userId;
    }

    public void setDstUserId(String userId) {
        mDstUserId = userId;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

}
