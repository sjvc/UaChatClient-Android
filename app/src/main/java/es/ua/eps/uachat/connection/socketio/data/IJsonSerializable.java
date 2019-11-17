package es.ua.eps.uachat.connection.socketio.data;

import org.json.JSONObject;

/*
    Interfaz que ha de definir cualquier clase que queramos que sea serializable usando JSON
 */
public interface IJsonSerializable<T> {
    JSONObject toJSON();
    T fromJSON(JSONObject json);
}
