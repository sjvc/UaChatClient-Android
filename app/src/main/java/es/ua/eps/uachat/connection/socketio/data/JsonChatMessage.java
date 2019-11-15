package es.ua.eps.uachat.connection.socketio.data;

import org.json.JSONObject;
import es.ua.eps.uachat.connection.base.data.ChatMessage;


public class JsonChatMessage extends ChatMessage implements IJsonSerializable {
    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public void fromJSON(JSONObject json) {

    }
}
