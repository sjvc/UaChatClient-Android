package es.ua.eps.uachat.fragments;

public class UserListFragment extends BaseChatConnectionFragment {

    public UserListFragment() {
        // Los fragments necesitan un constructor vacío público
    }

    // Los fragments hay que instanciarlos solo usando el constructor vacío, nunca con parámetros,
    // porque a veces Android re-crea un fragment previamente destruido (pero manteniendo el Bundle enviado con setArguments)
    // Así que si tenemos que pasar parámetros, los añadiremos a este método, y éste los pasa
    // al fragment con un Bundle, mediante setArguments. Y se recogen en onCreate.
    public static UserListFragment newInstance() {
        return new UserListFragment();
    }
}
