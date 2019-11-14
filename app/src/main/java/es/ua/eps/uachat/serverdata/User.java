package es.ua.eps.uachat.serverdata;

public class User {
    private String mId;
    private String mNombre;

    public User(String id, String nombre) {
        mId = id;
        mNombre = nombre;
    }

    public String getId() {
        return mId;
    }

    public String getNombre() {
        return mNombre;
    }
}
