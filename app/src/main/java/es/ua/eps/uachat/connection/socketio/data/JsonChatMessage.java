package es.ua.eps.uachat.connection.socketio.data;

import org.json.JSONException;
import org.json.JSONObject;

import es.ua.eps.uachat.connection.base.data.ChatMessage;
import es.ua.eps.uachat.connection.base.data.ChatUser;

/**
 * Esta clase lo único que hace es serializar y deserializar un objeto ChatMessage
 */
public class JsonChatMessage {
    private final static String JSON_SRC_USER_ID          = "srcUserId";
    private final static String JSON_SRC_USER_NAME        = "srcUserName";
    private final static String JSON_DST_USER_ID          = "dstUserId";
    private final static String JSON_DST_USER_NAME        = "dstUserName";
    private final static String JSON_MESSAGE              = "message";
    private final static String JSON_TIMESTAMP            = "timestamp";

    public static JSONObject toJSON(ChatMessage chatMessage) {
        try{
            JSONObject json = new JSONObject();
            json.put(JSON_SRC_USER_ID, chatMessage.getUser().getId());
            json.put(JSON_SRC_USER_NAME, chatMessage.getUser().getName());
            json.put(JSON_DST_USER_ID, chatMessage.getDstUser().getId());
            json.put(JSON_DST_USER_NAME, chatMessage.getDstUser().getName());
            json.put(JSON_MESSAGE, chatMessage.getMessage());
            json.put(JSON_TIMESTAMP, chatMessage.getTimestamp());
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ChatMessage fromJSON(JSONObject json) {
        ChatMessage chatMessage = new ChatMessage();

        try{
            chatMessage.setUser(new ChatUser(json.getString(JSON_SRC_USER_ID), json.getString(JSON_SRC_USER_NAME)));
            chatMessage.setDstUser(new ChatUser(json.getString(JSON_DST_USER_ID), json.getString(JSON_DST_USER_NAME)));
            chatMessage.setMessage(json.getString(JSON_MESSAGE));
            chatMessage.setTimestamp(json.getLong(JSON_TIMESTAMP));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chatMessage;
    }
}
