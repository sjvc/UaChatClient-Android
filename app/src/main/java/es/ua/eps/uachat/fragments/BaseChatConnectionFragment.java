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

/**
    Clase base para los fragments que han de interactuar con el servidor.
    Cuando se muestra el fragment se suscribe para recibir eventos de conexión, y cuando se oculta se desuscribe.
    Creamos un método llamado onResumeLoggedIn que se ejecuta una vez que se ha mostrado el fragment y además el usuario se ha logueado.
 */
public abstract class BaseChatConnectionFragment extends Fragment implements IChatConnectionListener {
    private IChatConnection mConnection;
    private ChatUser mUser;
    private boolean mResumed = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mConnection = ((UaChatApplication)getActivity().getApplication()).getChatConnection();
        mUser = ((UaChatApplication)getActivity().getApplication()).getChatUser();
    }

    @Override
    public void onResume() {
        super.onResume();

        mResumed = true;

        mConnection.addListener(this);

        if (mConnection.isLoggedIn()) {
            onResumeLoggedIn();
        }
    }

    @Override
    public void onStop() {
        mConnection.removeListener(this);

        super.onStop();
    }

    // Este método se ejecuta cuando se ejecuta onResume u onLoggedIn (el que se ejecute el último)
    // Si se ejecuta primero onResume, esperamos a estar logueados
    // Si se ejecuta primero onLoggedIn, esperamos a que se haga el onResume
    // Por tanto, cuando se ejecute, significará que estamos conectados, y el fragment está visible
    public abstract void onResumeLoggedIn();

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
        if (mResumed) {
            onResumeLoggedIn();
        }
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
