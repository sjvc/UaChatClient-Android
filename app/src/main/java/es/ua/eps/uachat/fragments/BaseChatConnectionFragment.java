package es.ua.eps.uachat.fragments;

import androidx.fragment.app.Fragment;

import es.ua.eps.uachat.connection.base.IChatConnectionListener;
import es.ua.eps.uachat.connection.base.data.ChatMessage;
import es.ua.eps.uachat.connection.base.data.ChatUser;

public abstract class BaseChatConnectionFragment extends Fragment implements IChatConnectionListener {

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
    public void onMessageListReceived(ChatMessage[] list) {

    }

    @Override
    public void onMessageReceived(ChatMessage message) {

    }

    @Override
    public void onUserListReceived(ChatUser[] list) {

    }
}
