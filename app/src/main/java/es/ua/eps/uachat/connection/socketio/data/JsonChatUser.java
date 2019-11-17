package es.ua.eps.uachat.connection.socketio.data;

import org.json.JSONException;
import org.json.JSONObject;

import es.ua.eps.uachat.connection.base.data.ChatUser;

public class JsonChatUser {
    private final static String JSON_ID           = "id";
    private final static String JSON_NAME         = "name";
    private final static String JSON_IS_CONNECTED = "isConnected";

    public static JSONObject toJSON(ChatUser chatUser) {
        try{
            JSONObject json = new JSONObject();
            json.put(JSON_ID, chatUser.getId());
            json.put(JSON_NAME, chatUser.getName());
            json.put(JSON_IS_CONNECTED, chatUser.isConnected());
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ChatUser fromJSON(JSONObject json) {
        ChatUser chatUser = new ChatUser();

        try{
            chatUser.setId(json.getString(JSON_ID));
            chatUser.setName(json.getString(JSON_NAME));
            chatUser.setIsConnected(json.getBoolean(JSON_IS_CONNECTED));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chatUser;
    }
}
