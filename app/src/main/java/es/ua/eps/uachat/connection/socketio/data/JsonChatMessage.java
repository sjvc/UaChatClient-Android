package es.ua.eps.uachat.connection.socketio.data;

import org.json.JSONException;
import org.json.JSONObject;
import es.ua.eps.uachat.connection.base.data.ChatMessage;
import es.ua.eps.uachat.connection.base.data.ChatUser;


public class JsonChatMessage extends ChatMessage implements IJsonSerializable<JsonChatMessage> {
    private final static String JSON_SRC_USER_ID          = "srcUserId";
    private final static String JSON_SRC_USER_NAME        = "srcUserName";
    private final static String JSON_DST_USER_ID          = "dstUserId";
    private final static String JSON_DST_USER_NAME        = "dstUserName";
    private final static String JSON_MESSAGE              = "message";
    private final static String JSON_TIMESTAMP            = "timestamp";

    @Override
    public JSONObject toJSON() {
        try{
            JSONObject json = new JSONObject();
            json.put(JSON_SRC_USER_ID, mSrcUser.getId());
            json.put(JSON_SRC_USER_NAME, mSrcUser.getName());
            json.put(JSON_DST_USER_ID, mDstUser.getId());
            json.put(JSON_DST_USER_NAME, mDstUser.getName());
            json.put(JSON_MESSAGE, mMessage);
            json.put(JSON_TIMESTAMP, mTimestamp);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JsonChatMessage fromJSON(JSONObject json) {
        try{
            mSrcUser = new ChatUser(json.getString(JSON_SRC_USER_ID), json.getString(JSON_SRC_USER_NAME));
            mDstUser = new ChatUser(json.getString(JSON_DST_USER_ID), json.getString(JSON_DST_USER_NAME));
            mMessage = json.getString(JSON_MESSAGE);
            mTimestamp = json.getLong(JSON_TIMESTAMP);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return this;
    }
}
