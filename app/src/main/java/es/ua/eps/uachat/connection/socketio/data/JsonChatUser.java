package es.ua.eps.uachat.connection.socketio.data;

import org.json.JSONException;
import org.json.JSONObject;

import es.ua.eps.uachat.connection.base.data.ChatUser;

public class JsonChatUser extends ChatUser implements IJsonSerializable {
    private final static String JSON_ID           = "id";
    private final static String JSON_NAME         = "name";
    private final static String JSON_IS_CONNECTED = "isConnected";

    @Override
    public JSONObject toJSON() {
        try{
            JSONObject json = new JSONObject();
            json.put(JSON_ID, getId());
            json.put(JSON_NAME, getName());
            json.put(JSON_IS_CONNECTED, mIsConnected);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void fromJSON(JSONObject json) {
        try{
            mId = json.getString(JSON_ID);
            mName = json.getString(JSON_NAME);
            mIsConnected = json.getBoolean(JSON_IS_CONNECTED);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
