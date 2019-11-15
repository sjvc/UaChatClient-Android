package es.ua.eps.uachat.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;

public class ChatFragment extends BaseChatConnectionFragment {
    private static final String ARG_CHAT_USER_ID = "ARG_CHAT_USER_ID";
    private static final String ARG_CHAT_USER_NAME = "ARG_CHAT_USER_NAME";

    private String mChatUserId;
    private String mChatUserName;

    public ChatFragment() {
       // Los fragments necesitan un constructor vacío público
    }

    // Los fragments hay que instanciarlos solo usando el constructor vacío, nunca con parámetros,
    // porque a veces Android re-crea un fragment previamente destruido (pero manteniendo el Bundle enviado con setArguments)
    // Así que si tenemos que pasar parámetros, los añadiremos a este método, y éste los pasa
    // al fragment con un Bundle, mediante setArguments. Y se recogen en onCreate.
    public static ChatFragment newInstance(String chatUserId, String chatUserName) {
        Bundle args = new Bundle();
        args.putString(ARG_CHAT_USER_ID, chatUserId);
        args.putString(ARG_CHAT_USER_NAME, chatUserName);

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mChatUserId = getArguments().getString(ARG_CHAT_USER_ID);
            mChatUserName = getArguments().getString(ARG_CHAT_USER_NAME);
        }
    }
}
