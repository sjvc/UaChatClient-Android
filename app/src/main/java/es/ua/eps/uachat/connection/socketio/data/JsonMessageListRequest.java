package es.ua.eps.uachat.connection.socketio.data;

import org.json.JSONException;
import org.json.JSONObject;

import es.ua.eps.uachat.connection.base.data.ChatMessageListRequest;

public class JsonMessageListRequest extends ChatMessageListRequest implements IJsonSerializable<JsonMessageListRequest> {
    private final static String JSON_SRC_USER_ID  = "srcUserId";
    private final static String JSON_DST_USER_ID  = "dstUserId";
    private final static String JSON_TIMESTAMP    = "timestamp";

    @Override
    public JSONObject toJSON() {
        try{
            JSONObject json = new JSONObject();
            json.put(JSON_SRC_USER_ID, mSrcUserId);
            json.put(JSON_DST_USER_ID, mDstUserId);
            json.put(JSON_TIMESTAMP, mTimestamp);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JsonMessageListRequest fromJSON(JSONObject json) {
        try{
            mSrcUserId = json.getString(JSON_SRC_USER_ID);
            mDstUserId = json.getString(JSON_DST_USER_ID);
            mTimestamp = json.getLong(JSON_TIMESTAMP);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return this;
    }
}
