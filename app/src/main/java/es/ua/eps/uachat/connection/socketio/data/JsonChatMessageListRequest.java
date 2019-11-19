package es.ua.eps.uachat.connection.socketio.data;

import org.json.JSONException;
import org.json.JSONObject;

import es.ua.eps.uachat.connection.base.data.ChatMessageListRequest;

/**
 * Esta clase lo único que hace es serializar y deserializar un objeto ChatMessageListRequest
 */
public class JsonChatMessageListRequest {
    private final static String JSON_SRC_USER_ID  = "srcUserId";
    private final static String JSON_DST_USER_ID  = "dstUserId";
    private final static String JSON_TIMESTAMP    = "timestamp";

    public static JSONObject toJSON(ChatMessageListRequest request) {
        try{
            JSONObject json = new JSONObject();
            json.put(JSON_SRC_USER_ID, request.getSrcUserId());
            json.put(JSON_DST_USER_ID, request.getDstUserId());
            json.put(JSON_TIMESTAMP, request.getTimestamp());
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ChatMessageListRequest fromJSON(JSONObject json) {
        ChatMessageListRequest request = new ChatMessageListRequest();

        try{
            request.setSrcUserId(json.getString(JSON_SRC_USER_ID));
            request.setDstUserId(json.getString(JSON_DST_USER_ID));
            request.setTimestamp(json.getLong(JSON_TIMESTAMP));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return request;
    }
}
