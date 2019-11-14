package es.ua.eps.uachat.serverdata;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private final static String JSON_ID   = "id";
    private final static String JSON_NAME = "name";

    private String mId;
    private String mName;

    public User(String id, String name) {
        mId = id;
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public JSONObject toJSON() {
        try{
            JSONObject json = new JSONObject();
            json.put(JSON_ID, mId);
            json.put(JSON_NAME, mName);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User fromJSON(JSONObject json) {
        try{
            return new User(json.getString(JSON_ID), json.getString(JSON_NAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
