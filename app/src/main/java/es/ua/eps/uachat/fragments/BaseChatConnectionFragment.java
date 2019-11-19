package es.ua.eps.uachat.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import es.ua.eps.uachat.UaChatApplication;
import es.ua.eps.uachat.connection.base.IChatConnection;
import es.ua.eps.uachat.connection.base.IChatConnectionListener;
import es.ua.eps.uachat.connection.base.data.ChatMessage;
import es.ua.eps.uachat.connection.base.data.ChatUser;

/*
    Clase base para los fragments que han de interactuar con el servidor.
 */
public abstract class BaseChatConnectionFragment extends Fragment implements IChatConnectionListener {
    private IChatConnection mConnection;
    private ChatUser mUser;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mConnection = ((UaChatApplication)getActivity().getApplication()).getChatConnection();
        mUser = ((UaChatApplication)getActivity().getApplication()).getChatUser();
    }

    @Override
    public void onResume() {
        mConnection.addListener(this);

        super.onResume();
    }

    @Override
    public void onStop() {
        mConnection.removeListener(this);

        super.onStop();
    }

    public IChatConnection getChatConnection() {
        return mConnection;
    }

    public ChatUser getUser() {
        return mUser;
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onConnectionError() {

    }

    @Override
    public void onConnectionTimeOut() {

    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onMessageListReceived(List<ChatMessage> messages) {

    }

    @Override
    public void onMessageReceived(ChatMessage message) {

    }

    @Override
    public void onUserListReceived(List<ChatUser> users) {

    }
}
