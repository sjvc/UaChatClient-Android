package es.ua.eps.uachat.connection.socketio.data;

import org.json.JSONObject;

public interface IJsonSerializable {
    JSONObject toJSON();
    void fromJSON(JSONObject json);
}
